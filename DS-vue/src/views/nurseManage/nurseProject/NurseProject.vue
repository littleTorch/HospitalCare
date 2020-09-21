<template>
    <el-main>
        <el-form size="mini" :model="selectFrom" label-width="80px">
            <el-row>
                <el-col :span="8">
                    <el-form-item label="项目名称:">
                        <el-input v-model="selectFrom.projectName" placeholder="请输入所需查询项目名称"></el-input>
                    </el-form-item>
                </el-col>
                <el-button @click="selectByLike" style="margin-left: 20px;" size="mini" type="primary" icon="el-icon-search">查询</el-button>
                <el-button @click="addOne" size="mini" type="primary" icon="el-icon-plus">新增护理等级信息</el-button>
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
                :data="nurseProjectTable"
                tooltip-effect="dark"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                size="mini"
                :height="tableHeight"
                border>
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="projectName" label="项目名称"></el-table-column>
            <el-table-column prop="price" label="价格"></el-table-column>
            <el-table-column prop="details" label="描述" width="200"></el-table-column>
            <el-table-column prop="addService" label="是否增值" :formatter="addFormat"></el-table-column>
            <el-table-column prop="status" label="状态" :formatter="statusFormat" ></el-table-column>
            <el-table-column label="操作" width="250" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="success" @click="updateValuables(scope.row)">编辑</el-button>
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

        <el-dialog title="新增护理项目" :visible.sync="Visible" width="25%">
            <el-form size="mini" ref="addOneData" :model="addOneData" label-width="85px">
                <el-form-item label="项目名称">
                    <el-input v-model="addOneData.projectName" placeholder="请输入新护理项目名称"></el-input>
                </el-form-item>
                <el-form-item label="价格">
                    <el-input v-model="addOneData.price" placeholder="请输入项目价格"></el-input>
                </el-form-item>
                <el-form-item label="是否增值">
                    <el-radio-group v-model="addOneData.addService">
                        <el-radio :label="0">否</el-radio>
                        <el-radio :label="1">是</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="addOneData.details" type="textarea" placeholder="请输入项目相关描述"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="Visible = false">取 消</el-button>
                <el-button type="primary" @click="addOneSubmit">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="编辑护理等级信息" :visible.sync="updateOneValuablesVisible" width="30%">
            <el-form
                    ref="updateData"
                    :model="updateData"
                    size="mini"
                    style="text-align:center"
                    :inline="true"
                    label-width="100px">
                <el-input type="hidden" v-model="updateData.id"></el-input>
                <el-form-item label="项目名称">
                    <el-input v-model="updateData.projectName" placeholder="请输入新护理项目名称"></el-input>
                </el-form-item>
                <el-form-item label="价格">
                    <el-input v-model="updateData.price" placeholder="请输入项目价格"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="updateData.details" type="textarea" placeholder="请输入项目相关描述"></el-input>
                </el-form-item>
                <el-form-item label="是否增值">
                    <el-radio-group v-model="updateData.addService">
                        <el-radio :label="0">否</el-radio>
                        <el-radio :label="1">是</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="状态">
                    <el-switch
                            v-model="statusVaule"
                            active-color="#13ce66"
                            inactive-color="#ff4949">
                    </el-switch>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="updateOneValuablesVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateSubmit">确 定</el-button>
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
                if(this.selectFrom.projectName == ""){
                    this.getTableData(1);
                }else{
                    let self = this
                    self.$axios.get('api/nurseProject/selectByLike',{
                        params: {
                            projectName: this.selectFrom.projectName,
                            pageSize: 10,
                            currentPage: 1
                        }
                    }).then((result) => {
                        if(result.data.code == 200){
                            self.nurseProjectTable = result.data.data.records;
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
                        this.$axios.post("api/nurseProject/insertOne", qs.stringify(this.addOneData)).then((result) => {
                            if (result.data.code == 200) {
                                this.$message({
                                    type: "success",
                                    duration: 1000,
                                    message: result.data.msg,
                                });
                                this.Visible = false;
                                this.$refs.addOneData.resetFields(); // 刷新表单
                                let num = parseInt(this.page.totalCount/this.page.pageSize)+1;
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
                if(this.statusVaule == true){
                    this.updateData.status = 1;
                }else{
                    this.updateData.status = 2;
                }
                this.$refs.updateData.validate((valid) => {
                    if (valid) {
                        this.$axios.put("api/nurseProject/updateOne", qs.stringify(this.updateData)).then((result) => {
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
            //根据id获取
            getProjectById(id,level){
                this.$axios.get('api/nurseProject/selectById',{
                    params:{
                        id: id,
                    }
                }).then(res=>{
                    if(res.data.code == 200){
                        this.detailsData = res.data.data;
                        if(this.detailsData.status ==1){
                            this.detailsData.status = "开启"
                        }else{
                            this.detailsData.status = "停用"
                        }
                        if(this.detailsData.addService == 0){
                            this.detailsData.addService = "否"
                        }else{
                            this.detailsData.addService = "是"
                        }
                        this.detailsData.level = level;
                    }
                })
            },
            //编辑
            updateValuables(row) {
                this.updateOneValuablesVisible = true;
                this.updateData = row;
                if (row.status ==1){
                    this.statusVaule = true;
                } else {
                    this.statusVaule = false;
                }
            },
            //单个删除
            deleteOne(valId) {
                this.$axios({
                    method: "delete",
                    url: "api/nurseProject/deleteOne",
                    params: {
                        valId: valId,
                    },
                }).then((result) => {
                    if (result.data.code == 200) {
                        this.$message({
                            type: "success",
                            duration: 1000,
                            message: result.data.msg,
                        });
                        if (((this.page.totalCount-1)%this.page.pageSize==0)&&(this.page.currentPage!=1)){
                            this.getTableData(this.page.currentPage-1);
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
                        url: "api/nurseProject/delSelected",
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
                            if (((this.page.totalCount-this.ids.length)%this.page.pageSize==0)&&(this.page.currentPage!=1)){
                                this.getTableData(this.page.currentPage-1);
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
                self.$axios.get('api/nurseProject/getNurseProjectList', {
                    params: {
                        pageSize: 10,
                        currentPage: pageNo
                    }
                }).then(res => {
                    if (res.data.code == 200) {
                        self.nurseProjectTable = res.data.data.records;
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
            //状态显示
            statusFormat(row){
                if(row.status == 1){
                    return "启用"
                }else{
                    return "停用"
                }
            },
            //增值显示
            addFormat(row){
                if(row.addService == 1){
                    return "是"
                }else{
                    return "否"
                }
            }
        },

        data() {
            return {
                statusVaule: "",
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
                project: [],
                detailsData: {},
                updateData: {
                    updateBy: "",
                    status: ""
                },
                projectData: [],
                tableChecked: [], //选中显示的值
                ids: [],
                selectFrom: {
                    projectName: "",},
                //表格数据
                nurseProjectTable: [],
                valuablesDetails: {},
            };
        },
        created: function () {
            this.getTableData(1);
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