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
            <el-table-column
                    align="center"
                    :formatter="stateFormat"
                    prop="bedStatus"
                    label="当前状态"
            ></el-table-column>
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
                            @onConfirm="deleteOne(scope.row.roomId)"
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

        <el-dialog title="新增宿舍" :visible.sync="addOneRoomVisible" width="40%">
            <el-form
                    ref="addOneForm"
                    size="mini"
                    style="text-align:center"
                    :model="addOneForm"
                    :inline="true"
                    label-width="130px"
            >
                <el-form-item label="宿舍号">
                    <el-input v-model="addOneForm.roomNo" placeholder="请输入宿舍号"></el-input>
                </el-form-item>
                <el-form-item label="宿舍楼">
                    <el-input v-model="addOneForm.roomBld" placeholder="请输入居住最大人数"></el-input>
                </el-form-item>
                <el-form-item label="房间分布（楼层）">
                    <el-input v-model="addOneForm.roomAddress" placeholder="请输入房间所在楼层"></el-input>
                </el-form-item>
                <el-form-item label="宿舍费用">
                    <el-input v-model="addOneForm.roomCost" placeholder="宿舍费用"></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="房间类型">
                    <el-radio-group v-model="addOneForm.roomType">
                        <el-radio :label="0">4人间</el-radio>
                        <el-radio :label="1">6人间</el-radio>
                        <el-radio :label="2">8人间</el-radio>
                    </el-radio-group>
                </el-form-item>
                <br/>
                <el-form-item label="是否满员">
                    <el-radio-group v-model="addOneForm.roomState">
                        <el-radio :label="1">是</el-radio>
                        <el-radio :label="0">否</el-radio>
                    </el-radio-group>
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
                <el-form-item label="宿舍号">
                    <el-input v-model="updateOneForm.roomNo" placeholder="请输入宿舍号"></el-input>
                </el-form-item>
                <el-form-item label="宿舍楼">
                    <el-input v-model="updateOneForm.roomBld" placeholder="请输入居住最大人数"></el-input>
                </el-form-item>
                <el-form-item label="宿舍类型">
                    <el-input v-model="updateOneForm.roomAddress" placeholder="请输入居住最大人数"></el-input>
                </el-form-item>
                <el-form-item label="宿舍费用">
                    <el-input v-model="updateOneForm.roomCost" placeholder="宿舍费用"></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="房间类型">
                    <el-radio-group v-model="updateOneForm.roomType">
                        <el-radio :label="0">4人间</el-radio>
                        <el-radio :label="1">6人间</el-radio>
                        <el-radio :label="2">8人间</el-radio>
                    </el-radio-group>
                </el-form-item>
                <br/>
                <el-form-item label="是否满员">
                    <el-radio-group v-model="updateOneForm.roomState">
                        <el-radio :label="0">空</el-radio>
                        <el-radio :label="1">满</el-radio>
                        <el-radio :label="2">未满</el-radio>
                    </el-radio-group>
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
                <el-form-item label="宿舍号">
                    <el-input v-model="bed.roomNo" placeholder="请输入宿舍号"></el-input>
                </el-form-item>
                <el-form-item label="宿舍楼">
                    <el-input v-model="bed.roomBld" placeholder="请输入居住最大人数"></el-input>
                </el-form-item>
                <el-form-item label="宿舍类型">
                    <el-input v-model="bed.roomAddress" placeholder="请输入居住最大人数"></el-input>
                </el-form-item>
                <el-form-item label="宿舍费用">
                    <el-input v-model="bed.roomCost" placeholder="宿舍费用"></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="房间类型">
                    <el-radio-group v-model="bed.roomType">
                        <el-radio :label="0">4人间</el-radio>
                        <el-radio :label="1">6人间</el-radio>
                        <el-radio :label="2">8人间</el-radio>
                    </el-radio-group>
                </el-form-item>
                <br/>
                <el-form-item label="是否满员">
                    <el-radio-group v-model="bed.roomState">
                        <el-radio :label="0">空</el-radio>
                        <el-radio :label="1">满</el-radio>
                        <el-radio :label="2">未满</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <el-row style="text-align: center">
                <el-popover
                        placement="top"
                        width="300"
                        trigger="click">
                    <el-table
                            :data="bed.stu"
                            size="mini"
                            style="width: 100%"
                            tooltip-effect="dark">
                        <el-table-column  property="chStuNo" label="学号"></el-table-column>
                        <el-table-column  property="chStuName" label="姓名"></el-table-column>
                        <el-table-column  property="chBed" label="床位"></el-table-column>
                    </el-table>
                    <el-button slot="reference">居住学生详情</el-button>
                </el-popover>
            </el-row>

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
                this.roomVisible=true;
                this.$axios({
                    method: "get",
                    url: "api/bed/bed",
                    params:{
                        chBld:row.roomBld,
                        chRoom:row.roomNo
                    }
                }).then((result) => {
                    this.bed={
                        roomNo: row.roomNo,
                        roomBld: row.roomBld,
                        roomAddress: row.roomAddress,
                        roomType: row.roomType,
                        roomState: row.roomState,
                        roomCost:row.roomCost,
                        stu: result.data
                    };
                })
            },
            setOne(){
                this.$axios({
                    method: "put",
                    url: "api/bed/updateOneRoom",
                    data: JSON.stringify(this.updateOneForm),
                    headers:{'Content-Type':'application/json;charsetset=UTF-8'}
                }).then((result) => {
                    if (result.data == "ok") {
                        this.updateOneRoomVisible = false;
                        this.sel();
                        this.$message({
                            type: "success",
                            duration: 1000,
                            message: "修改成功",
                        });
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
                        ids.push(id.roomId);
                    });
                    this.dele(ids);
                })
            },
            dele(ids){
                this.$axios({
                    method: "delete",
                    url: "api/bed/delRooms",
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
            deleteOne(roomId) {
                let ids = [];
                ids.push(roomId);
                this.dele(ids);
            },
            addOneRoom() {
                this.$refs.addOneForm.validate((valid) => {
                    if (valid) {
                        var data = new FormData();
                        data.append('roomNo', this.addOneForm.roomNo);
                        data.append('roomBld', this.addOneForm.roomBld);
                        data.append('roomAddress', this.addOneForm.roomAddress);
                        data.append('roomCost', this.addOneForm.roomCost);
                        data.append('roomType', this.addOneForm.roomType);
                        data.append('roomState', this.addOneForm.roomState);
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
                switch (row.roomType) {
                    case 0:
                        return "普通";
                    case 1:
                        return "特殊";
                }
            },
            stateFormat(row) {
                switch (row.roomState) {
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
                    roomNo: "",
                    roomBld: '',
                    roomAddress: '',
                    roomType: '',
                    roomState: '',
                    roomCost:'',
                    stu:[],
                },
                roomVisible:false,
                tableChecked: [],
                addOneRoomVisible: false,
                updateOneRoomVisible: false,
                addOneForm: {
                    roomNo: "",
                    roomBld: '',
                    roomAddress: '',
                    roomType: '',
                    roomState: '',
                    roomCost:''
                },
                //搜索框数据绑定
                bedFrom: {
                    floor: "",
                    roomNo:'',
                },
                updateOneForm: [
                    {
                        roomNo: "",
                        roomBld: '',
                        roomAddress: '',
                        roomType: '',
                        roomState: '',
                        roomCost:''
                    },
                ],
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