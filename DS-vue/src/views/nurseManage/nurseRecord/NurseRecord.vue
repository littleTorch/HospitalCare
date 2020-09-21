<template>
    <el-main>
        <el-form size="mini" :model="selectFrom" label-width="80px">
            <el-row>
                <el-col :span="8">
                    <el-form-item label="顾客id:">
                        <el-input v-model="selectFrom.cusId" placeholder="请输入所需查询顾客姓名"></el-input>
                    </el-form-item>
                </el-col>
                <el-button @click="selectByLike" style="margin-left: 20px;" size="mini" type="primary" icon="el-icon-search">查询</el-button>
                <el-button @click="addOne" size="mini" type="primary" icon="el-icon-plus">新增护理记录</el-button>
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
                :data="nurseRecordTable"
                tooltip-effect="dark"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                size="mini"
                :height="tableHeight"
                border>
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="cusName" label="顾客"></el-table-column>
            <el-table-column prop="projectName" label="项目名称"></el-table-column>
            <el-table-column prop="projectTime" label="开始护理时间"></el-table-column>
            <el-table-column prop="empName" label="护理人员"></el-table-column>
            <el-table-column label="操作" width="350" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="success" @click="itemDetails(scope.row)">项目详情</el-button>
                    <el-button style="margin-left: 30px" size="mini" type="success" @click="updateValuables(scope.row)">编辑</el-button>
                    <el-popconfirm
                            style="margin-left: 30px;"
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

        <el-dialog title="新增护理记录" :visible.sync="Visible" width="25%">
            <el-form size="mini" ref="addOneData" :model="addOneData" label-width="85px">
                <el-form-item label="顾客姓名">
                    <el-select v-model="addOneData.cusId" placeholder="请选择顾客">
                        <el-option
                                v-for="item in cus"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="项目名称">
                    <el-select v-model="addOneData.projectId" placeholder="请选择项目">
                        <el-option
                                v-for="item in project"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="数量">
                    <el-input v-model="addOneData.num" placeholder="请输入购买数量"></el-input>
                </el-form-item>
                <el-form-item label="执行时间">
                    <el-date-picker
                            v-model="addOneData.projectTime"
                            type="datetime"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="护理人员">
                    <el-select v-model="addOneData.empId" placeholder="请选择护理人员">
                        <el-option
                                v-for="item in emp"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="addOneData.remark" type="textarea" placeholder="备注"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="Visible = false">取 消</el-button>
                <el-button type="primary" @click="addOneSubmit">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="编辑护理等级信息" :visible.sync="updateOneValuablesVisible" width="40%">
            <el-form
                    ref="updateData"
                    :model="updateData"
                    size="mini"
                    style="text-align:center"
                    :inline="true"
                    label-width="100px">
                <el-input type="hidden" v-model="updateData.id"></el-input>
                <el-form-item label="顾客姓名">
                    <el-select v-model="updateData.cusId" placeholder="请选择顾客">
                        <el-option
                                v-for="item in cus"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="项目名称">
                    <el-select v-model="updateData.projectId" placeholder="请选择项目">
                        <el-option
                                v-for="item in project"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="数量">
                    <el-input v-model="updateData.num" placeholder="请输入购买数量"></el-input>
                </el-form-item>
                <el-form-item label="执行时间">
                    <el-date-picker
                            v-model="updateData.projectTime"
                            type="datetime"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="护理人员">
                    <el-select v-model="updateData.empId" placeholder="请选择护理人员">
                        <el-option
                                v-for="item in emp"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="updateData.remark" type="textarea" placeholder="备注"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="updateOneValuablesVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateSubmit">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="项目记录详情信息" :visible.sync="detailsVisible" width="40%">
            <el-form
                    :model="detailsData"
                    size="mini"
                    style="text-align:center"
                    :inline="true"
                    label-width="100px">
                <el-form-item label="顾客姓名">
                    <el-input v-model="detailsData.cusName" disabled></el-input>
                </el-form-item>
                <el-form-item label="项目名称">
                    <el-input v-model="detailsData.projectName" disabled></el-input>
                </el-form-item>
                <el-form-item label="数量">
                    <el-input v-model="detailsData.num" disabled></el-input>
                </el-form-item>
                <el-form-item label="项目开始时间">
                    <el-input v-model="detailsData.projectTime" disabled></el-input>
                </el-form-item>
                <el-form-item label="护理人员姓名">
                    <el-input v-model="detailsData.empName" disabled></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="detailsData.remark" type="textarea" disabled></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="detailsVisible = false">取 消</el-button>
      </span>
        </el-dialog>


    </el-main>
</template>

<script>

    import qs from 'qs';

    export default {
        methods: {
            //详情
            itemDetails(row) {
                this.detailsData = row;
                this.openDetailsVisible();
            },
            openDetailsVisible() {
                this.detailsVisible = true;
            },
            addOne() {
                this.Visible = true;
            },
            //模糊查询
            selectByLike() {
                if (this.selectFrom.cusId == " ") {
                    this.getTableData(1);
                } else {
                    let self = this;
                    self.$axios.get('api/nurseRecord/selectByLike', {
                        params: {
                            cusId: this.selectFrom.cusId,
                            pageSize: 10,
                            currentPage: 1
                        }
                    }).then((result) => {
                        if (result.data.code == 200) {
                            self.nurseRecordTable = result.data.data.records;
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
                        this.$axios.post("api/nurseRecord/insertOne", qs.stringify(this.addOneData)).then((result) => {
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
                        this.$axios.put("api/nurseRecord/updateOne", qs.stringify(this.updateData)).then((result) => {
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
            //编辑
            updateValuables(row) {
                this.updateOneValuablesVisible = true;
                this.updateData = row;
            },
            //单个删除
            deleteOne(id) {
                this.$axios({
                    method: "delete",
                    url: "api/nurseRecord/deleteOne",
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
                        url: "api/nurseRecord/delSelected",
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
                self.$axios.get('api/nurseRecord/getNurseRecordList', {
                    params: {
                        pageSize: 10,
                        currentPage: pageNo
                    }
                }).then(res => {
                    if (res.data.code == 200) {
                        self.nurseRecordTable = res.data.data.records;
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
            //获取项目信息
            getProjectData() {
                this.$axios({
                    method: "get",
                    url: "api/nurseProject/nurseProjectList",
                }).then((res) => {
                    if (res.data.code == 200) {
                        this.projectData = res.data.data;
                        for (let i = 0; i < this.projectData.length; i++) {
                            let projectId = {
                                value: this.projectData[i].id,
                                label: this.projectData[i].projectName,
                            }
                            this.project.push(projectId);
                        }
                    }
                });
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
            //获取护理人员名单
            getEmpData() {
                this.$axios({
                    method: "get",
                    url: "api/nurseRecord/getEmpName",
                }).then((res) => {
                    if (res.data.code == 200) {
                        this.empData = res.data.data;
                        console.log(this.empData);
                        for (let i = 0; i < this.empData.length; i++) {
                            let empId = {
                                value: this.empData[i].id,
                                label: this.empData[i].empName,
                            }
                            this.emp.push(empId);
                        }
                    }
                });
            },
        },
        data() {
            return {
                pickerOptions: {

                },
                detailsVisible: false,
                username: "",
                page: {
                    currentPage: 1, // 当前页
                    pageSize: 10, // 每页显示条目个数
                    totalCount: 0 // 总条目数
                },
                Visible: false,
                title: "",
                addOneData: {
                    createBy: JSON.parse(sessionStorage.getItem("user")).userName,
                    status: 1
                },
                tableHeight: 0,
                updateOneValuablesVisible: false,
                addManyValuablesVisible: false,
                detailsData: {},
                updateData: {
                    updateBy: "",
                    status: ""
                },
                projectData: [],
                cusData:[],
                empData:[],
                tableChecked: [], //选中显示的值
                ids: [],
                selectFrom: {
                    cusId: "",},
                //表格数据
                nurseRecordTable: [],
                valuablesDetails: {},
                cus:[],
                emp:[],
                project: [],
            };
        },
        created: function () {
            this.getTableData(1);
            this.getProjectData();
            this.getCusData();
            this.getEmpData();
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