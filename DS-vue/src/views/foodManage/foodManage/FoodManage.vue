<template>

    <el-main>
        <!--搜索框-->
        <el-header height="50px" >
            <el-form size="mini" :model="foodManage" label-width="80px">
                <el-row>
                    <el-col :span="5">

                        <el-form-item style="font-size: 15px" label="姓名:">
                            <el-select v-model="foodManage.cusName" placeholder="请选择客户">
                                <el-option
                                        v-for="item in cusNames"
                                        :key="item.cusName"
                                        :value="item.cusName"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-button @click="findFoodManage(foodManage.cusName)"  style="margin-left: 20px;" size="mini" type="primary" icon="el-icon-search">查询</el-button>
                    <el-button @click="addDetail" size="mini" type="primary" icon="el-icon-plus">新增食物日历</el-button>
                </el-row>
            </el-form>
        </el-header>

        <!--数据表格-->
        <el-table v-if="user!=''"  ref="findManageData" tooltip-effect="dark" :data="findManageData.slice((currentPage-1) * pageSize ,currentPage * pageSize)" @selection-change="handleSelectionChange" size="mini" :height="tableHeight" border style="width: 100%">

            <el-table-column align="center" type="selection" width="43"></el-table-column>
            <el-table-column align="center" :formatter="nameFormat" prop="" label="档案号" ></el-table-column>
            <el-table-column align="center" :formatter="dateFormat" prop="date" label="星期" ></el-table-column>
            <el-table-column align="center" :formatter="dayFormat" prop="day" label="所处时间段" ></el-table-column>
            <el-table-column label="食物" width="170" align="center">
                <template slot-scope="scope">
                    <el-button @click="details(scope.row.food)" type="primary" size="mini">详情</el-button>
                  </template>
            </el-table-column>
            <el-table-column label="操作" width="230" align="center">
                <template slot-scope="scope">
                    <el-button @click="findOne(scope.row)" type="success" size="mini">编辑</el-button>
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
                :page-size="pageSize"
                layout="total, prev, pager, next"
                :total="findManageData.length"
                v-if="user!=''"
        ></el-pagination>

        <el-dialog title="新增膳食日历数据" :visible.sync="addFoodDateVisible" width="60%">
            <el-form size="mini" ref="addOne" style="text-align:left" :model="addOne" :inline="true" label-width="100px">
                <el-form-item label="客户姓名">
                    <el-input v-model="addOne.cusName" placeholder="" disabled></el-input>
                </el-form-item>
                <br />
                <el-form-item label="星期">
                    <el-radio-group v-model="addOne.date">
                        <el-radio :label="0">星期日</el-radio>
                        <el-radio :label="1">星期一</el-radio>
                        <el-radio :label="2">星期二</el-radio>
                        <el-radio :label="3">星期三</el-radio>
                        <el-radio :label="4">星期四</el-radio>
                        <el-radio :label="5">星期五</el-radio>
                        <el-radio :label="6">星期六</el-radio>

                    </el-radio-group>

                </el-form-item>
                <br />
                <el-form-item label="所处时间段">
                    <el-radio-group v-model="addOne.day">
                        <el-radio :label="0">早餐</el-radio>
                        <el-radio :label="1">午餐</el-radio>
                        <el-radio :label="2">晚餐</el-radio>
                    </el-radio-group>

                </el-form-item>
                <br />
                <el-form-item label="食物名称">
                    <el-input v-model="addOne.foodName" placeholder="请输入食物名称" ></el-input>
                </el-form-item>
                <br />
                <el-form-item label="食物类型">
                    <el-radio-group v-model="addOne.foodType">
                        <el-radio :label="0">大荤</el-radio>
                        <el-radio :label="1">小荤</el-radio>
                        <el-radio :label="2">素菜</el-radio>
                        <el-radio :label="3">甜点</el-radio>
                        <el-radio :label="4">水果</el-radio>
                        <el-radio :label="5">套餐</el-radio>
                    </el-radio-group>

                </el-form-item>
                <br />
                <el-form-item label="食物标签">
                    <el-input v-model="addOne.foodTag" placeholder="请输入食物标签" ></el-input>
                </el-form-item>
                <br />
                <el-form-item label="食物价格">
                    <el-input v-model="addOne.foodPrice" placeholder="请输入食物价格" ></el-input>
                </el-form-item>
                <br />
                <el-form-item label="食物图片">
                    <el-input v-model="addOne.foodPic" placeholder="请输入食物图片" ></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="是否清蒸">
                    <el-radio-group v-model="addOne.isSteam" >
                        <el-radio :label="0">否</el-radio>
                        <el-radio :label="1">是</el-radio>
                    </el-radio-group>

                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="addFoodDateVisible = false">取 消</el-button>
        <el-button @click="addOneSubmit()" type="primary" >确 定</el-button>
      </span>
        </el-dialog>

        <el-dialog title="食物详情数据" :visible.sync="detailFoodVisible" width="60%">

            <el-form disabled size="mini" ref="foodDetail" style="text-align:left" :model="foodDetail" :inline="true" label-width="100px">
                <el-form-item label="食物名称">
                    <el-input v-model="foodDetail.foodName" placeholder="请输入食物名称" ></el-input>
                </el-form-item>
                <br />
                <el-form-item label="食物类型">
                    <el-radio-group v-model="foodDetail.foodType">
                        <el-radio :label="0">大荤</el-radio>
                        <el-radio :label="1">小荤</el-radio>
                        <el-radio :label="2">素菜</el-radio>
                        <el-radio :label="3">甜点</el-radio>
                        <el-radio :label="4">水果</el-radio>
                        <el-radio :label="5">套餐</el-radio>
                    </el-radio-group>

                </el-form-item>
                <br />
                <el-form-item label="食物标签">
                    <el-input v-model="foodDetail.foodTag" placeholder="请输入食物标签" ></el-input>
                </el-form-item>
                <br />
                <el-form-item label="食物价格">
                    <el-input v-model="foodDetail.foodPrice" placeholder="请输入食物价格" ></el-input>
                </el-form-item>
                <br />
                <el-form-item label="食物图片">
                    <el-input v-model="foodDetail.foodPic" placeholder="请输入食物图片" ></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="是否清蒸">
                    <el-radio-group v-model="foodDetail.isSteam" >
                        <el-radio :label="0">否</el-radio>
                        <el-radio :label="1">是</el-radio>
                    </el-radio-group>

                </el-form-item>

            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="detailFoodVisible = false">取 消</el-button>
            </span>
        </el-dialog>

        <el-dialog title="修改当前膳食数据" :visible.sync="updateFoodVisible" width="60%">

            <el-form size="mini" ref="updateFoodData" style="text-align:left" :model="updateFoodData" :inline="true" label-width="100px">
                <el-form-item label="id">
                    <el-input disabled v-model="updateFoodData.id" placeholder="请输入id"></el-input>
                </el-form-item>
                <br />
                <el-form-item label="星期">
                    <el-radio-group v-model="updateFoodData.date">
                        <el-radio :label="0">星期日</el-radio>
                        <el-radio :label="1">星期一</el-radio>
                        <el-radio :label="2">星期二</el-radio>
                        <el-radio :label="3">星期三</el-radio>
                        <el-radio :label="4">星期四</el-radio>
                        <el-radio :label="5">星期五</el-radio>
                        <el-radio :label="6">星期六</el-radio>

                    </el-radio-group>

                </el-form-item>
                <br />
                <el-form-item label="所处时间段">
                    <el-radio-group v-model="updateFoodData.day">
                        <el-radio :label="0">早餐</el-radio>
                        <el-radio :label="1">午餐</el-radio>
                        <el-radio :label="2">晚餐</el-radio>
                    </el-radio-group>

                </el-form-item>
                <br />
                <el-form-item label="食物名称">
                    <el-input v-model="updateFoodDataObj.foodName" placeholder="请输入食物名称" ></el-input>
                </el-form-item>
                <br />
                <el-form-item label="食物类型">
                    <el-radio-group v-model="updateFoodDataObj.foodType">
                        <el-radio :label="0">大荤</el-radio>
                        <el-radio :label="1">小荤</el-radio>
                        <el-radio :label="2">素菜</el-radio>
                        <el-radio :label="3">甜点</el-radio>
                        <el-radio :label="4">水果</el-radio>
                        <el-radio :label="5">套餐</el-radio>
                    </el-radio-group>

                </el-form-item>
                <el-form-item label="食物标签">
                    <el-input v-model="updateFoodDataObj.foodTag" placeholder="请输入食物标签" ></el-input>
                </el-form-item>
                <el-form-item label="食物价格">
                    <el-input v-model="updateFoodDataObj.foodPrice" placeholder="请输入食物价格" ></el-input>
                </el-form-item>
                <el-form-item label="食物图片">
                    <el-input v-model="updateFoodDataObj.foodPic" placeholder="请输入食物图片" ></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="是否清蒸">
                    <el-radio-group v-model="updateFoodDataObj.isSteam" >
                        <el-radio :label="0">否</el-radio>
                        <el-radio :label="1">是</el-radio>
                    </el-radio-group>

                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
            <el-button @click="updateFoodVisible = false">取 消</el-button>
            <el-button @click="updateSubmit" type="primary" >确 定</el-button>
          </span>
        </el-dialog>

    </el-main>



</template>

<script>
    export default {
        data() {
            return {
                user: "",
                mode: {},
                pageSize: 10,
                currentPage: 1,
                //搜索框数据绑定
                foodManage: {
                    cusName: "",
                },
                // 表格高度
                tableHeight: 0,
                // 表格数据
                tableChecked: [],//选中显示的值
                ids:[],//批量删除id
                addFoodDateVisible: false,
                detailFoodVisible: false,
                updateFoodVisible: false,
                addOne: {
                    cusName: "",
                    foodPic: "mifan.jpg"
                },
                findManageData: [],
                foodDetail: {},
                updateFoodData: {},
                updateFoodDataObj: {
                    foodPic: "mifan.jpg",
                },
                reId: "",
                cusNames: [],
            };
        },
        methods: {
            //pageSize改变调用
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            // 页数改变调用
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage = val;
            },
            resetForm(formName) {
                if (this.$refs[formName]) {
                    this.$refs[formName].resetFields();
                }
            },
            handleSelectionChange(val){
                // console.log(val);
                this.tableChecked = val;
            },

            dateFormat(row) {
                if (row.date == 0) {
                    return "星期日";
                } else if (row.date == 1) {
                    return "星期一";
                } else if (row.date == 2){
                    return "星期二";
                } else if (row.date == 3){
                    return "星期三";
                } else if (row.date == 4){
                    return "星期四";
                } else if (row.date == 5){
                    return "星期五";
                } else{
                    return "星期六";
                }
            },
            dayFormat(row){
                if (row.day == 0) {
                    return "早餐";
                }else if (row.day == 1) {
                    return "午餐";
                }else if (row.day == 2) {
                    return "晚餐";
                }
            },

            nameFormat(row){
                return this.cusName
            },
            details(food){
                //console.log(food);
                this.foodDetail = food;
                //console.log(this.foodDetail);
                this.detailFoodVisible = true;
            },

            findFoodManage(cusName){
                //console.log(recordId)
                this.$axios({
                    method: "post",
                    url: "api/foodManage/foodManageList",
                    params: {
                        cusName: cusName,
                    }

                }).then((res) => {
                    //console.log(res)

                    if (res.data.code == 200){
                        this.$message({
                            type: "success",
                            duration: 1000,
                            message: res.data.msg
                        });
                        this.addOne.cusName = this.foodManage.cusName;
                        this.cusName =  this.foodManage.cusName;
                        this.findManageData = res.data.data;
                        this.user = "user";
                        //this.findManageData.recordId = this.foodManage.recordId;
                        //console.log(this.findManageData)

                    } else {
                        this.$message({
                            type: "error",
                            duration: 1000,
                            message: res.data.msg
                        })
                    }
                })
            },

            addDetail(){
                this.addFoodDateVisible = true
            },
            addOneSubmit(){
                //console.log(this.addOne);
                this.$refs.addOne.validate((valid) => {
                    if(valid){
                        var data = new FormData();
                        data.append('cusName',this.addOne.cusName);
                        data.append('date',this.addOne.date);
                        data.append('day',this.addOne.day);
                        data.append('foodName',this.addOne.foodName);
                        data.append('foodType',this.addOne.foodType);
                        data.append('foodTag',this.addOne.foodTag);
                        data.append('foodPrice',this.addOne.foodPrice);
                        data.append('foodPic',this.addOne.foodPic);
                        data.append('isSteam',this.addOne.isSteam);

                        console.log(data);
                        this.$axios({
                            method: "post",
                            url: "api/foodManage/addOneFoodDate",
                            data: data
                        }).then((result) => {
                            if (result.data.code == 200) {
                                this.$message({
                                    type: "success",
                                    duration: 1000,
                                    message: result.data.msg,
                                });
                                this.addFoodDateVisible = false;
                                this.$refs.addOne.resetFields(); // 刷新表单
                                this.findFoodManage(this.addOne.cusName);
                                this.addOne={};
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
            findOne(row){
                this.updateFoodVisible = true;
                this.updateFoodData = row;
                this.updateFoodDataObj = row.food;
                console.log(this.updateFoodDataObj);
            },
            updateSubmit(){
                this.updateFoodData.updateFoodDataObj = this.updateFoodDataObj
                //console.log(this.updateFoodData);
                this.$refs.updateFoodData.validate((valid) => {
                    if(valid){
                        var data = new FormData();
                        data.append('id',this.updateFoodData.id);
                        data.append('date',this.updateFoodData.date);
                        data.append('day',this.updateFoodData.day);
                        data.append('foodName',this.updateFoodData.updateFoodDataObj.foodName);
                        data.append('foodType',this.updateFoodData.updateFoodDataObj.foodType);
                        data.append('foodTag',this.updateFoodData.updateFoodDataObj.foodTag);
                        data.append('foodPrice',this.updateFoodData.updateFoodDataObj.foodPrice);
                        data.append('foodPic',this.updateFoodData.updateFoodDataObj.foodPic);
                        data.append('isSteam',this.updateFoodData.updateFoodDataObj.isSteam);
                        //console.log(data);
                        this.$axios({
                            method: "post",
                            url: "api/foodManage/updateFoodDate",
                            data: data
                        }).then((result) => {
                            if (result.data.code == 200) {
                                this.$message({
                                    type: "success",
                                    duration: 1000,
                                    message: result.data.msg,
                                });
                                this.updateFoodVisible = false;
                                this.$refs.updateFoodData.resetFields(); // 刷新表单
                                this.findFoodManage(this.addOne.cusName);
                                this.updateFoodData={};
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
            deleteOne(id){
                // console.log(id)
                this.$axios({
                    method: "post",
                    url: "api/foodManage/deleteOne",
                    params: {
                        foodDateId: id,
                    }
                }).then((result) => {
                    if (result.data.code == 200) {
                        this.$message({
                            type: "success",
                            duration: 1000,
                            message: result.data.msg,
                        });
                        this.findFoodManage(this.addOne.cusName);
                    } else {
                        this.$message({
                            type: "error",
                            duration: 1000,
                            message: result.data.msg,
                        });
                    }
                });
            },
            getCustomer(){
                this.$axios({
                    method: "post",
                    url: "api/foodDate/foodAllCustomer"
                }).then((result) =>{
                    this.cusNames = result.data.data;
                    console.log(this.cusNames);
                })

            }
        },
        created: function () {
            this.getCustomer();
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