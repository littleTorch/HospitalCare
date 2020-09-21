<template>
    <el-main>
        <el-form size="mini" :model="selectFrom" label-width="80px">
            <el-row>
                <el-col :span="8">
                    <el-form-item label="护理等级:">
                        <el-input v-model="selectFrom.level" placeholder="请输入所需查询信息等级"></el-input>
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
                :data="nurseLevelTable"
                tooltip-effect="dark"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                size="mini"
                :height="tableHeight"
                border>
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="level" label="护理等级"></el-table-column>
            <el-table-column prop="projectId" label="项目id"></el-table-column>
            <el-table-column prop="status" label="状态" :formatter="statusFormat" ></el-table-column>
            <el-table-column label="操作" width="350" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="primary" @click="itemDetails(scope.row)">项目详情</el-button>
                    <el-button size="mini" type="success" @click="updateValuables(scope.row)">编辑</el-button>
                    <el-popconfirm
                            style="margin-left: 10px;"
                            title="您确定删除吗？"
                            icon="el-icon-info"
                            iconColor="red"
                            @onConfirm="deleteOne(scope.row.id)"
                    >
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

        <el-dialog title="新增护理等级信息" :visible.sync="Visible" width="25%">
            <el-form size="mini" ref="addOneData" :model="addOneData" label-width="85px">
                <el-form-item label="等级">
                    <el-input v-model="addOneData.level" placeholder="请输入新项目所属等级"></el-input>
                </el-form-item>
                <el-form-item label="项目">
                    <el-select v-model="addOneData.projectId" placeholder="请选择项目">
                        <el-option
                                v-for="item in project"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
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
                <el-form-item label="等级">
                    <el-input v-model="updateData.level"></el-input>
                </el-form-item>
                <el-form-item label="护理项目">
                    <el-select v-model="updateData.projectId">
                        <el-option
                                v-for="item in project"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
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

        <el-dialog title="项目详情信息" :visible.sync="detailsVisible" width="40%">
        <el-form
                :model="detailsData"
                size="mini"
                style="text-align:center"
                :inline="true"
                label-width="100px">
            <el-form-item label="护理等级">
                <el-input v-model="detailsData.level" disabled></el-input>
            </el-form-item>
            <el-form-item label="护理项目名称">
                <el-input v-model="detailsData.projectName" disabled></el-input>
            </el-form-item>
            <el-form-item label="价格">
                <el-input v-model="detailsData.price" disabled></el-input>
            </el-form-item>
            <el-form-item label="描述">
                <el-input v-model="detailsData.details" disabled></el-input>
            </el-form-item>
            <el-form-item label="是否增值">
                <el-input v-model="detailsData.addService" disabled></el-input>
            </el-form-item>
            <el-form-item label="状态">
                <el-input v-model="detailsData.status" :formatter="statusFormat" disabled></el-input>
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
            addOne() {
                this.Visible = true;
            },
            //获取护理项目
            getProjectData(){
                this.$axios({
                    method: "get",
                    url: "api/nurseProject/nurseProjectList"
                }).then((res) =>{
                    if(res.data.code == 200){
                        this.projectData = res.data.data;
                        for(let i=0;i<this.projectData.length;i++){
                            let projectId = {
                                value:this.projectData[i].id,
                                label:this.projectData[i].projectName,
                            }
                            this.project.push(projectId);
                        }
                    }
                });
            },
            //模糊查询
            selectByLike() {
                if(this.selectFrom.level == ""){
                    this.getTableData(1);
                }else{
                    let self = this
                    self.$axios.get('api/nurseLevel/selectByLike2',{
                        params: {
                            level: this.selectFrom.level,
                            pageSize: 10,
                            currentPage: 1
                        }
                    }).then((result) => {
                        if(result.data.code == 200){
                            self.nurseLevelTable = result.data.data.records;
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
                        this.$axios.post("api/nurseLevel/insertOne", qs.stringify(this.addOneData)).then((result) => {
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
                        this.$axios.put("api/nurseLevel/updateOne", qs.stringify(this.updateData)).then((result) => {
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
            //详情
            itemDetails(row) {
                this.getProjectById(row.projectId,row.level);
                this.detailsVisible = true;
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
                    url: "api/nurseLevel/deleteOne",
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
                        url: "api/nurseLevel/delSelected",
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
                self.$axios.get('api/nurseLevel/getNurseLevelList', {
                    params: {
                        pageSize: 10,
                        currentPage: pageNo
                    }
                }).then(res => {
                    if (res.data.code == 200) {
                        self.nurseLevelTable = res.data.data.records;
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
                detailsVisible: false,
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
                    level: "",},
                //表格数据
                nurseLevelTable: [],
                valuablesDetails: {},
            };
        },
        created: function () {
            this.getTableData(1);
            this.getProjectData();
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