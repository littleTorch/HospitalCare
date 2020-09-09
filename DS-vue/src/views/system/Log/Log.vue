<template>
    <el-main>
        <el-form size="mini" :model="logForm" label-width="80px">
            <el-row>
                <el-col :span="5">
                    <el-form-item label="操作用户:">
                        <el-input v-model="logForm.logUser"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="5">
                    <el-form-item label="操作表:">
                        <el-input v-model="logForm.logTable"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="5">
                    <el-form-item label="操作类型:">
                        <el-select v-model="logForm.logType" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in options"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-button @click="getTableData(1)" style="margin-left: 20px;" size="mini" type="primary" icon="el-icon-search">查询
                </el-button>
                <el-button @click="del" size="mini" type="primary" icon="el-icon-delete">批量删除</el-button>
            </el-row>
        </el-form>

        <el-table
                ref="historyTable"
                :data="logTable"
                tooltip-effect="dark"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                size="mini"
                :height="tableHeight"
                border
        >
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="操作用户" prop="logUser"></el-table-column>
            <el-table-column prop="logTable" label="操作表格"></el-table-column>
            <el-table-column :formatter="TypeFormat" prop="logType" label="操作类型"></el-table-column>
            <el-table-column prop="logParameter" label="操作参数" show-overflow-tooltip></el-table-column>
            <el-table-column prop="createTime" label="操作时间" sortable></el-table-column>
            <el-table-column label="操作" width="150" align="center">
                <template slot-scope="scope">
                    <el-button @click="selectOne(scope.row)" size="mini" type="primary">详情</el-button>
                    <el-popconfirm @onConfirm="handleEdit(scope.row)" style="margin-left: 10px;" title="您确定删除吗？"
                                   icon="el-icon-info" iconColor="red">
                        <el-button  size="mini" type="danger" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="page.currentPage"
                :page-size="page.pageSize"
                :total="page.totalCount"
                layout="total, prev, pager, next"
        ></el-pagination>

        <el-dialog
                title="详情"
                :visible.sync="dialogVisible"
                width="30%">

            <el-form
                    size="mini"
                    style="text-align:center"
                    :model="log"
                    :inline="true"
                    label-width="100px"
                    disabled
            >
                <el-form-item label="操作用户">
                    <el-input v-model="log.logUser"></el-input>
                </el-form-item>
                <el-form-item label="操作表">
                    <el-input v-model="log.logTable"></el-input>
                </el-form-item>
                <el-form-item label="操作类型">
                    <el-radio-group v-model="log.logType">
                        <el-radio :label="0">查询</el-radio>
                        <el-radio :label="1">新增</el-radio>
                        <el-radio :label="2">修改</el-radio>
                        <el-radio :label="3">删除</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="操作参数">
                    <el-input type="textarea" autosize v-model="log.logParameter"></el-input>
                </el-form-item>
                <el-form-item label="操作时间">
                    <el-input v-model="log.createTime"></el-input>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
        </el-dialog>
    </el-main>
</template>

<script>
    export default {
        created() {
           this.getTableData(1);
        },
        methods: {
            select(){
                this.$axios({
                    method: "get",
                    url: "api/log"
                }).then((result) => {
                    this.logTable = result.data;
                })
            },
            handleEdit(row){
                console.log(row);
                let ids = [];
                ids.push(row.id);
                this.$axios({
                    method: "delete",
                    url: "api/log/del",
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    },
                    data: JSON.stringify(ids),
                }).then((res) => {
                    if (res.data.code == 200){
                        this.$message({
                            type: "success",
                            message: res.data.msg
                        })
                        if (((this.page.totalCount-ids.length)%this.page.pageSize==0)&&(this.page.currentPage!=1)){
                            this.getTableData(this.page.currentPage-1);
                        } else {
                            this.getTableData(this.page.currentPage);
                        }
                    } else {
                        this.$message({
                            type: "error",
                            message: res.data.msg
                        })
                    }
                })
            },
            del() {
                this.$confirm('是否删除所选信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    console.log(this.tableChecked)
                    let ids = [];
                    this.tableChecked.forEach(id => {
                        ids.push(id.id);
                    });
                    console.log(this.tableChecked);
                    console.log(ids);
                    this.$axios({
                        method: "delete",
                        url: "api/log/del",
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        },
                        data: JSON.stringify(ids),
                    }).then((res) => {
                        if (res.data.code == 200){
                            this.$message({
                                type: "success",
                                message: res.data.msg
                            })
                            if (((this.page.totalCount-ids.length)%this.page.pageSize==0)&&(this.page.currentPage!=1)){
                                this.getTableData(this.page.currentPage-1);
                            } else {
                                this.getTableData(this.page.currentPage);
                            }
                        } else {
                            this.$message({
                                type: "error",
                                message: res.data.msg
                            })
                        }
                    })
                })
            },
            selectOne(row) {
                this.log = row;
                this.dialogVisible = true;
            },
            TypeFormat(row) {
                switch (row.logType) {
                    case 0:
                        return "查询";
                    case 1:
                        return "新增";
                    case 2:
                        return "修改";
                    case 3:
                        return "删除";
                }
            },
            //表格前的复选框点击事件
            handleSelectionChange(val) {
                this.tableChecked = val;
            },
            //pageSize改变调用
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            getTableData(pageNo) {
                let self = this
                self.$axios.get('api/log/select', {
                    params: {
                        logUser: this.logForm.logUser,
                        logTable: this.logForm.logTable,
                        logType: this.logForm.logType,
                        pageSize: 10,
                        currentPage: pageNo
                    }
                }).then(res => {
                    if (res.data.code == 200) {
                        self.logTable = res.data.data.records;
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
        },
        data() {
            return {
                logTable: [],
                log: {},
                options: [{
                    value: '0',
                    label: '查询'
                }, {
                    value: '1',
                    label: '新增'
                }, {
                    value: '2',
                    label: '修改'
                }, {
                    value: '3',
                    label: '删除'
                }],
                value: '',
                logForm: {
                    logUser: '',
                    logTable: '',
                    logType: '',
                },

                page: {
                    currentPage: 1, // 当前页
                    pageSize: 10, // 每页显示条目个数
                    totalCount: 0 // 总条目数
                },

                pageSize: 10,
                currentPage: 1,
                tableHeight: 0,
                tableChecked: [],
                dialogVisible: false
            }
        },
        mounted() {
            this.$nextTick(() => {
                this.tableHeight = window.innerHeight - 270;
            });
        },
    }
</script>

<style scoped>

</style>