<template>
    <el-main>
        <el-form size="mini" :model="selectFrom" label-width="100px">
            <el-row>
                <el-col :span="6">
                    <el-form-item label="外出人员id:">
                        <el-input v-model="selectFrom.cusId" placeholder="请输入所需查询外出人员id"></el-input>
                    </el-form-item>
                </el-col>
                <el-button @click="selectByLike" style="margin-left: 20px;" size="mini" type="primary" icon="el-icon-search">查询</el-button>
                <el-button @click="addOne" size="mini" type="primary" icon="el-icon-plus">新增外出登记</el-button>
                <el-button
                        @click="deleteList"
                        size="mini"
                        type="primary"
                        icon="el-icon-delete"
                        :disabled="this.tableChecked.length === 0"
                >批量删除</el-button>
            </el-row>
        </el-form>
        <el-table
                :data="leaveTable"
                tooltip-effect="dark"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                size="mini"
                :height="tableHeight"
                border>
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="cusName" label="外出人员姓名"></el-table-column>
            <el-table-column prop="leaveTime" width="90" label="外出时间"></el-table-column>
            <el-table-column prop="expectTime" width="90" label="预计回院时间"></el-table-column>
            <el-table-column prop="actualTime" width="90" label="实际回院时间"></el-table-column>
            <el-table-column prop="accompany" label="陪同人"></el-table-column>
            <el-table-column prop="accompanyPhone" label="陪同人联系电话"></el-table-column>
            <el-table-column prop="leaveReason" label="外出原因"></el-table-column>
            <el-table-column prop="auditPerson" label="审批人"></el-table-column>
            <el-table-column prop="audit" label="审批状态" :formatter="auditFormat"></el-table-column>
            <el-table-column prop="auditTime" width="90" label="审批时间"></el-table-column>
            <el-table-column label="操作" width="250" align="center">
                <template slot-scope="scope">
                    <el-button :disabled="scope.row.audit == 0 ? false: true" size="mini" type="success" @click="auditValuables(scope.row)">审核</el-button>
                    <el-button size="mini" type="success" @click="updateValuables(scope.row)">编辑</el-button>
                    <el-popconfirm
                            style="margin-left: 10px;"
                            title="您确定删除吗？"
                            icon="el-icon-info"
                            iconColor="red"
                            @onConfirm="deleteOne(scope.row.id)">
                        <el-button size="mini" type="danger" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <!--分页组件
          size-change: 当page-sizes 改变时触发事件
          current-change：当页数发生变化时触发事件，
          current-page：当前是第几页
          page-size：页容量，也就是每页多少条数据
          total:总共有多少条数据,后台返回数据数值
        -->
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="page.currentPage"
                :page-size="page.pageSize"
                :total="page.totalCount"
                layout="total, prev, pager, next"
        ></el-pagination>

        <el-dialog title="新增外出登记" :visible.sync="Visible" width="25%">
            <el-form size="mini" ref="addOneData" :model="addOneData" label-width="100px">
                <el-form-item label="外出人员姓名">
                    <el-select v-model="addOneData.cusId" placeholder="请选择顾客">
                        <el-option
                                v-for="item in cus"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="外出时间">
                    <el-date-picker
                            v-model="addOneData.leaveTime"
                            type="datetime"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="外出日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="预计回院时间">
                    <el-date-picker
                            v-model="addOneData.expectTime"
                            type="datetime"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="预计回院时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="陪同人">
                    <el-input v-model="addOneData.accompany" placeholder="陪同人姓名"></el-input>
                </el-form-item>
                <el-form-item label="联系电话">
                    <el-input v-model="addOneData.accompanyPhone" placeholder="陪同人联系电话"></el-input>
                </el-form-item>
                <el-form-item label="外出原因">
                    <el-input v-model="addOneData.leaveReason" type="textarea" placeholder="外出原因"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="Visible = false">取 消</el-button>
                <el-button type="primary" @click="addOneSubmit">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="编辑外出记录" :visible.sync="updateOneValuablesVisible" width="40%">
            <el-form
                    ref="updateData"
                    :model="updateData"
                    size="mini"
                    style="text-align:center"
                    :inline="true"
                    label-width="100px">
                <el-input type="hidden" v-model="updateData.id"></el-input>
                <el-form-item label="外出人员姓名">
                    <el-select v-model="updateData.cusId" placeholder="请选择顾客">
                        <el-option
                                v-for="item in cus"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="外出时间">
                    <el-date-picker
                            v-model="updateData.leaveTime"
                            type="datetime"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="外出日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="预计回院时间">
                    <el-date-picker
                            v-model="updateData.expectTime"
                            type="datetime"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="预计回院时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="实际回院时间">
                    <el-date-picker
                            v-model="updateData.actualTime"
                            type="datetime"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="实际回院时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="陪同人">
                    <el-input v-model="updateData.accompany" placeholder="陪同人姓名"></el-input>
                </el-form-item>
                <el-form-item label="联系电话">
                    <el-input v-model="updateData.accompanyPhone" placeholder="陪同人联系电话"></el-input>
                </el-form-item>
                <el-form-item label="外出原因">
                    <el-input v-model="updateData.leaveReason" type="textarea" placeholder="外出原因"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="updateOneValuablesVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateSubmit">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="审核处理" :visible.sync="auditVisible" width="20%">
            <el-form
                    ref="auditData"
                    :model="auditData"
                    size="mini"
                    style="text-align:center"
                    :inline="true"
                    label-width="100px">
                <el-form-item label="审核情况">
                    <el-radio-group v-model="auditData.audit">
                        <el-radio :label="1">审核通过</el-radio>
                        <el-radio :label="2">审核未批准</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="auditSubmit">确 定</el-button>
                <el-button @click="detailsVisible = false">取 消</el-button>
            </span>
        </el-dialog>

    </el-main>
</template>

<script>

    import qs from 'qs';

    export default {
        methods: {
            addOne() {
                this.Visible = true;
            },
            //模糊查询
            selectByLike() {
                if (this.selectFrom.cusId == "") {
                    this.getTableData(1);
                } else {
                    let self = this;
                    self.$axios.get('api/leave/selectByLike', {
                        params: {
                            cusId: this.selectFrom.cusId,
                            pageSize: 10,
                            currentPage: 1
                        }
                    }).then((result) => {
                        if (result.data.code == 200) {
                            self.leaveTable = result.data.data.records;
                            self.$set(self.page, 'totalCount', result.data.data.total)
                            self.$set(self.page, 'pageSize', result.data.data.size)
                            self.$set(self.page, 'currentPage', result.data.data.current)
                        }
                    });
                }
            },
            //确认添加
            addOneSubmit() {
                this.$refs.addOneData.validate((valid) => {
                    if (valid) {
                        this.$axios.post("api/leave/insertOne", qs.stringify(this.addOneData)).then((result) => {
                            if (result.data.code == 200) {
                                this.$message({
                                    type: "success",
                                    duration: 1000,
                                    message: result.data.msg,
                                });
                                this.Visible = false;
                                this.$refs.addOneData.resetFields(); // 刷新表单
                                let num = parseInt(this.page.totalCount / this.page.pageSize) + 1;
                                this.getTableData(num);
                            } else {
                                this.$message({
                                    type: "error",
                                    duration: 1000,
                                    message: result.data.msg,
                                });
                            }
                        });
                    }
                });
            },
            //确认编辑
            updateSubmit() {
                this.updateData.updateBy = JSON.parse(sessionStorage.getItem("user")).userName;
                this.$refs.updateData.validate((valid) => {
                    if (valid) {
                        this.$axios.put("api/leave/updateOne", qs.stringify(this.updateData)).then((result) => {
                            if (result.data.code == 200) {
                                this.$message({
                                    type: "success",
                                    duration: 1000,
                                    message: result.data.msg,
                                });
                                this.updateOneValuablesVisible = false;
                                this.getTableData(this.page.currentPage);
                            } else {
                                this.$message({
                                    type: "error",
                                    duration: 1000,
                                    message: result.data.msg,
                                });
                            }
                        });
                    } else {
                        return false;
                    }
                });
            },
            //确认审核情况
            auditSubmit(){
                this.auditData.auditPerson = JSON.parse(sessionStorage.getItem("user")).userName;
                this.auditData.auditTime = this.dateFormat("yyyy-MM-dd HH:mm:ss",new Date());
                this.$refs.auditData.validate((valid) => {
                    if (valid) {
                        this.$axios.put("api/leave/updateOne", qs.stringify(this.auditData)).then((result) => {
                            if (result.data.code == 200) {
                                this.$message({
                                    type: "success",
                                    duration: 1000,
                                    message: result.data.msg,
                                });
                                this.auditVisible = false;
                                this.getTableData(this.page.currentPage);
                            } else {
                                this.$message({
                                    type: "error",
                                    duration: 1000,
                                    message: result.data.msg,
                                });
                            }
                        });
                    } else {
                        return false;
                    }
                });
            },
            //编辑
            updateValuables(row) {
                this.updateOneValuablesVisible = true;
                this.updateData = row;
            },
            //单个删除
            deleteOne(id) {
                this.$axios({
                    method: "delete",
                    url: "api/leave/deleteOne",
                    params: {
                        id: id
                    },
                }).then((result) => {
                    if (result.data.code == 200) {
                        this.$message({
                            type: "success",
                            duration: 1000,
                            message: result.data.msg,
                        });
                        if (((this.page.totalCount - 1) % this.page.pageSize == 0) && (this.page.currentPage != 1)) {
                            this.getTableData(this.page.currentPage - 1);
                        } else {
                            this.getTableData(this.page.currentPage);
                        }
                    } else {
                        this.$message({
                            type: "error",
                            duration: 1000,
                            message: result.data.msg,
                        });
                    }
                });
            },
            //批量删除
            deleteList() {
                this.$confirm("是否删除所选信息?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                }).then(() => {
                    this.tableChecked.forEach((row) => {
                        this.ids.push(row.id);
                    });
                    console.log(this.ids);
                    this.$axios({
                        method: "delete",
                        url: "api/leave/delSelected",
                        headers: {
                            "Content-Type": "application/json;charset=UTF-8",
                        },
                        data: JSON.stringify(this.ids),
                    }).then((result) => {
                        if (result.data.code == 200) {
                            this.$message({
                                type: "success",
                                duration: 1000,
                                message: result.data.msg,
                            });
                            if (((this.page.totalCount - this.ids.length) % this.page.pageSize == 0) && (this.page.currentPage != 1)) {
                                this.getTableData(this.page.currentPage - 1);
                            } else {
                                this.getTableData(this.page.currentPage);
                            }
                        } else {
                            this.$message({
                                type: "error",
                                duration: 1000,
                                message: result.data.msg,
                            });
                        }
                    });
                });
            },
            //复选框事件
            handleSelectionChange(val) {
                this.tableChecked = val;
            },
            //pageSize改变调用
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            //分页查询
            getTableData(pageNo) {
                let self = this
                self.$axios.get('api/leave/getLeaveList', {
                    params: {
                        pageSize: 10,
                        currentPage: pageNo
                    }
                }).then(res => {
                    if (res.data.code == 200) {
                        self.leaveTable = res.data.data.records;
                        self.$set(self.page, 'totalCount', res.data.data.total)
                        self.$set(self.page, 'pageSize', res.data.data.size)
                        self.$set(self.page, 'currentPage', res.data.data.current)
                    }
                });
            },
            // 页数改变调用
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.getTableData(val);
            },
            //获取顾客信息
            getCusData() {
                this.$axios({
                    method: "get",
                    url: "api/customer/getCustomerList",
                    params: {
                        pageSize: 10,
                        currentPage: 1
                    }
                }).then((res) => {
                    if (res.data.code == 200) {
                        this.cusData = res.data.data.records;
                        // console.log(this.cusData)
                        for (let i = 0; i < this.cusData.length; i++) {
                            let cusId = {
                                value: this.cusData[i].id,
                                label: this.cusData[i].cusName,
                            }
                            this.cus.push(cusId);
                        }
                    }
                });
            },
            //审核状态
            auditFormat(row){
              if (row.audit == 0){
                  return "未审核"
              }else if(row.audit == 1){
                  return "审核通过"
              }else{
                  return "审核未批准"
              }
            },
            //审核
            auditValuables(row){
                this.auditData = row;
                this.auditVisible = true;
            },
            dateFormat(fmt, date) {
                let ret;
                const opt = {
                    "y+": date.getFullYear().toString(),        // 年
                    "M+": (date.getMonth() + 1).toString(),     // 月
                    "d+": date.getDate().toString(),            // 日
                    "H+": date.getHours().toString(),           // 时
                    "m+": date.getMinutes().toString(),         // 分
                    "s+": date.getSeconds().toString()          // 秒
                    // 有其他格式化字符需求可以继续添加，必须转化成字符串
                };
                for (let k in opt) {
                    ret = new RegExp("(" + k + ")").exec(fmt);
                    if (ret) {
                        fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
                    }
                }
                return fmt;
            },
        },
        data() {
            return {
                pickerOptions: {
                },
                username: "",
                title: "",
                page: {
                    currentPage: 1, // 当前页
                    pageSize: 10, // 每页显示条目个数
                    totalCount: 0 // 总条目数
                },
                Visible: false,
                addOneData: {
                    createBy: JSON.parse(sessionStorage.getItem("user")).userName,
                    audit: 0,
                },
                tableHeight: 0,
                updateOneValuablesVisible: false,
                addManyValuablesVisible: false,
                auditVisible: false,
                updateData: {
                    updateBy: "",
                    status: ""
                },
                auditData: [],
                cusData:[],
                tableChecked: [], //选中显示的值
                ids: [],
                selectFrom: {
                    cusId: "",
                },
                //表格数据
                leaveTable: [],
                cus:[],
            };
        },
        created: function () {
            this.getTableData(1);
            this.getCusData();
            this.addOneData.createBy =JSON.parse(sessionStorage.getItem("user")).userName;
        },
        mounted() {
            this.$nextTick(() => {
                this.tableHeight = window.innerHeight - 270;
            });
        },
    };
</script>

<style>
</style>