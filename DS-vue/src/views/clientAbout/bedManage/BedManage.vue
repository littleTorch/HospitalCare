<template>
    <el-main>
        <!--搜索框-->
        <el-form size="mini" :model="bedFrom" label-width="80px">
            <el-row>
                <el-col :span="5">
                    <el-form-item label="楼层:">
                        <el-input v-model="bedFrom.floor" placeholder="请输入楼层"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="5">
                    <el-form-item label="宿舍号:">
                        <el-input v-model="bedFrom.roomNo" placeholder="请输入宿舍号"></el-input>
                    </el-form-item>
                </el-col>
                <el-button
                        style="margin-left: 20px;"
                        class="btn-left"
                        size="mini"
                        type="primary"
                        icon="el-icon-search"
                        @click="sel(1)"
                >查询
                </el-button>
                <el-button @click="AddOne" size="mini" type="primary" icon="el-icon-plus">单个新增</el-button>
                <el-button @click="deleteList" size="mini" type="primary" icon="el-icon-delete">批量删除</el-button>
            </el-row>
        </el-form>
        <!--数据表格-->
        <el-table
                :data="bedData"
                size="mini"
                :height="tableHeight"
                @selection-change="handleSelectionChange"
                border
                style="width: 100%"
                tooltip-effect="dark"
        >
            <el-table-column align="center" type="selection"></el-table-column>
            <el-table-column align="center" prop="floor" label="宿舍楼层"></el-table-column>
            <el-table-column align="center" prop="roomNo" label="宿舍号"></el-table-column>
            <el-table-column align="center" prop="bedNo" label="床位号"></el-table-column>
<!--            <el-table-column-->
<!--                    align="center"-->
<!--                    :formatter="stateFormat"-->
<!--                    prop="bedStatus"-->
<!--                    label="当前状态"-->
<!--            ></el-table-column>-->
            <el-table-column align="center" :formatter="typeFormat" prop="positionType" label="房间类型"></el-table-column>
            <el-table-column label="操作" width="250" align="center">
                <template slot-scope="scope">
                    <el-button @click="details(scope.row)" type="primary" size="mini">详情</el-button>
                    <el-button @click="updateRoom(scope.row)" type="success" size="mini">编辑</el-button>
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

        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="page.currentPage"
                :page-size="page.pageSize"
                :total="page.totalCount"
                layout="total, prev, pager, next"
        ></el-pagination>

        <el-dialog title="新增宿舍" :visible.sync="addOneRoomVisible" width="30%">
            <el-form
                    ref="addOneForm"
                    size="mini"
                    style="text-align:center"
                    :model="addOneForm"
                    :inline="true"
                    label-width="100px"
            >
                <el-form-item label="所属楼房：">
                    <el-input v-model="addOneForm.floor" placeholder="请输入宿舍号"></el-input>
                </el-form-item>
                <el-form-item label="房间号码：">
                    <el-input v-model="addOneForm.roomNo" placeholder="请输入居住最大人数"></el-input>
                </el-form-item>
                <el-form-item label="床位号：">
                    <el-input v-model="addOneForm.bedNo" placeholder="请输入房间所在楼层"></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="房间类型：">
                    <el-radio-group v-model="addOneForm.positionType">
                        <el-radio :label="0">普通</el-radio>
                        <el-radio :label="1">特殊</el-radio>
                    </el-radio-group>
                </el-form-item>
                <br/>
<!--                <el-form-item label="房间类型：">-->
<!--                    <el-radio-group v-model="addOneForm.bedStatus">-->
<!--                        <el-radio :label="0">空</el-radio>-->
<!--                        <el-radio :label="1">使用中</el-radio>-->
<!--                    </el-radio-group>-->
<!--                </el-form-item>-->
                <br/>
                <el-form-item label="备注：">
                    <el-input type="textarea" v-model="addOneForm.remark"/>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="addOneRoomVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOneRoom">确 定</el-button>
      </span>
        </el-dialog>

        <el-dialog title="修改宿舍" :visible.sync="updateOneRoomVisible" width="40%">
            <el-form
                    size="mini"
                    style="text-align:center"
                    :model="updateOneForm"
                    :inline="true"
                    label-width="100px"
            >
                <el-form-item label="所属楼房：">
                    <el-input v-model="updateOneForm.floor" placeholder="请输入宿舍号"></el-input>
                </el-form-item>
                <el-form-item label="房间号码：">
                    <el-input v-model="updateOneForm.roomNo" placeholder="请输入居住最大人数"></el-input>
                </el-form-item>
                <el-form-item label="床位号：">
                    <el-input v-model="updateOneForm.bedNo" placeholder="请输入房间所在楼层"></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="房间类型：">
                    <el-radio-group v-model="updateOneForm.positionType">
                        <el-radio :label="0">普通</el-radio>
                        <el-radio :label="1">特殊</el-radio>
                    </el-radio-group>
                </el-form-item>
                <br/>
<!--                <el-form-item label="房间类型：">-->
<!--                    <el-radio-group v-model="updateOneForm.bedStatus">-->
<!--                        <el-radio :label="0">空</el-radio>-->
<!--                        <el-radio :label="1">使用中</el-radio>-->
<!--                    </el-radio-group>-->
<!--                </el-form-item>-->
                <br/>
                <el-form-item label="备注：">
                    <el-input type="textarea" v-model="updateOneForm.remark"/>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="updateOneRoomVisible = false">取 消</el-button>
        <el-button @click="setOne" type="primary">确 定</el-button>
      </span>
        </el-dialog>
        
        <el-dialog title="宿舍详情" :visible.sync="roomVisible" width="40%">
            <el-form
                    size="mini"
                    style="text-align:center"
                    :model="bed"
                    :inline="true"
                    label-width="100px"
                    disabled
            >
                <el-form-item label="所属楼房：">
                    <el-input v-model="bed.floor" placeholder="请输入宿舍号"></el-input>
                </el-form-item>
                <el-form-item label="房间号码：">
                    <el-input v-model="bed.roomNo" placeholder="请输入居住最大人数"></el-input>
                </el-form-item>
                <el-form-item label="床位号：">
                    <el-input v-model="bed.bedNo" placeholder="请输入房间所在楼层"></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="房间类型：">
                    <el-radio-group v-model="bed.positionType">
                        <el-radio :label="0">普通</el-radio>
                        <el-radio :label="1">特殊</el-radio>
                    </el-radio-group>
                </el-form-item>
                <br/>
                <el-form-item label="房间类型：">
                    <el-radio-group v-model="bed.bedStatus">
                        <el-radio :label="0">空</el-radio>
                        <el-radio :label="1">使用中</el-radio>
                    </el-radio-group>
                </el-form-item>
                <br/>
                <el-form-item label="备注：">
                    <el-input type="textarea" v-model="bed.remark"/>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="roomVisible = false">取 消</el-button>
        <el-button @click="roomVisible = false" type="primary">确 定</el-button>
      </span>
        </el-dialog>
    </el-main>
</template>

<script>
    export default {
        created() {
            this.sel(1);
        },
        methods: {
            //详情
            details(row){
                console.log(row);
                this.roomVisible=true;
                this.bed={
                    floor: row.floor,
                    roomNo: row.roomNo,
                    bedNo: row.bedNo,
                    positionType: row.positionType,
                    bedStatus: row.bedStatus,
                    remark:row.remark,
                };
            },
            setOne(){
                this.$axios({
                    method: "put",
                    url: "api/bed/updateOne",
                    data: JSON.stringify(this.updateOneForm),
                    headers:{'Content-Type':'application/json;charsetset=UTF-8'}
                }).then((res) => {
                    if (res.data.code == 200){
                        this.updateOneRoomVisible = false;
                        this.$message({
                            type: "success",
                            duration: 1000,
                            message: res.data.msg
                        })
                        this.sel(this.page.currentPage);
                    } else {
                        this.$message({
                            type: "error",
                            duration: 1000,
                            message: res.data.msg
                        })
                    }
                })
            },
            deleteList() {
                this.$confirm('是否删除所选信息?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    let ids = [];
                    this.tableChecked.forEach(id => {
                        ids.push(id.id);
                    });
                    this.dele(ids);
                })
            },
            dele(ids){
                this.$axios({
                    method: "delete",
                    url: "api/bed/dels",
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    },
                    data: JSON.stringify(ids),
                }).then(res => {
                    if (res.data.code == 200){
                        this.$message({
                            type: "success",
                            message: res.data.msg
                        })
                        if (((this.page.totalCount-ids.length)%this.page.pageSize==0)&&(this.page.currentPage!=1)){
                            this.sel(this.page.currentPage-1);
                        } else {
                            this.sel(this.page.currentPage);
                        }
                    } else {
                        this.$message({
                            type: "error",
                            message: res.data.msg
                        })
                    }
                })
            },
            deleteOne(id) {
                let ids = [];
                ids.push(id);
                this.dele(ids);
            },
            addOneRoom() {
                this.$refs.addOneForm.validate((valid) => {
                    if (valid) {
                        var data = new FormData();
                        data.append('floor', this.addOneForm.floor);
                        data.append('roomNo', this.addOneForm.roomNo);
                        data.append('bedNo', this.addOneForm.bedNo);
                        data.append('positionType', this.addOneForm.positionType);
                        data.append('bedStatus', this.addOneForm.bedStatus);
                        data.append('remark', this.addOneForm.remark);
                        this.$axios({
                            method: "post",
                            url: "api/bed/addOneRoom",
                            data: data
                        }).then(res=>{
                            if (res.data.code == 200){
                                this.addOneRoomVisible = false;
                                this.$message({
                                    type: "success",
                                    message: res.data.msg
                                })
                                this.sel(this.page.currentPage);
                            } else {
                                this.$message({
                                    type: "error",
                                    message: res.data.msg
                                })
                            }
                        })
                    }
                });
            },
            sel(pageNo) {
                this.$axios({
                    method: "get",
                    url: "api/bed/selectBedList",
                    params: {
                        floor: this.bedFrom.floor,
                        roomNo: this.bedFrom.roomNo,
                        pageSize: 10,
                        currentPage: pageNo,
                    }
                }).then(res => {
                    if (res.data.code == 200) {
                        this.bedData = res.data.data.records;
                        this.page.totalCount=res.data.data.total;
                        this.page.pageSize=res.data.data.size;
                        this.page.currentPage=res.data.data.current;
                    }
                })
            },
            //弹出框
            AddOne() {
                this.addOneRoomVisible = true;
            },

            updateRoom(row) {
                this.updateOneRoomVisible = true;
                this.updateOneForm = Object.assign({}, row);
            },

            //表格格式
            typeFormat(row) {
                switch (row.positionType) {
                    case 0:
                        return "普通";
                    case 1:
                        return "特殊";
                }
            },
            stateFormat(row) {
                switch (row.bedStatus) {
                    case 0:
                        return "空";
                    case 1:
                        return "使用中";
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
            // 页数改变调用
            handleCurrentChange(val) {
                this.sel(val)
            },
            //重置表单内容
            resetForm(formName) {
                if (this.$refs[formName]) {
                    this.$refs[formName].resetFields();
                }
            },
        },
        mounted() {
            this.$nextTick(() => {
                this.tableHeight = window.innerHeight - 280;
            });
        },
        data() {
            return {
                bed:{
                    floor: '',
                    roomNo: '',
                    bedNo: '',
                    positionType: 0,
                    bedStatus: 0,
                    remark:''
                },
                roomVisible:false,
                tableChecked: [],
                addOneRoomVisible: false,
                updateOneRoomVisible: false,
                addOneForm: {
                    floor: '',
                    roomNo: '',
                    bedNo: '',
                    positionType: 0,
                    bedStatus: 0,
                    remark:''
                },
                //搜索框数据绑定
                bedFrom: {
                    floor: "",
                    roomNo:'',
                },
                updateOneForm: {
                    floor: '',
                    roomNo: '',
                    bedNo: '',
                    positionType: 0,
                    bedStatus: 0,
                    remark:''
                },
                // 表格高度
                tableHeight: 0,
                // 表格数据
                bedData: [],
                page: {
                    currentPage: 1, // 当前页
                    pageSize: 10, // 每页显示条目个数
                    totalCount: 0 // 总条目数
                },
            };
        },
    };
</script>

<style>
</style>