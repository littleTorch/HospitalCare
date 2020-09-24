<template>
    <el-main>
        <!--搜索框-->
        <el-form size="mini" :model="findFoodDate" label-width="80px">
            <el-row>
                <el-col :span="5">
                    <el-form-item style="font-size: 15px" label="姓名:">
                        <el-input v-model="findFoodDate.foodName" placeholder="请输入食物名字"></el-input>
                    </el-form-item>
                </el-col>
                <el-button @click="findFood(findFoodDate.foodName)"  style="margin-left: 20px;" size="mini" type="primary" icon="el-icon-search">查询</el-button>
                <el-button @click="addOneFood" size="mini" type="primary" icon="el-icon-plus">新增食物</el-button>
                <el-button @click="deleteList(tableChecked)" size="mini" type="primary" icon="el-icon-delete">批量删除</el-button>

            </el-row>
        </el-form>
        <!--数据表格-->
        <el-table v-if="user==''"  ref="foodData" tooltip-effect="dark" :data="foodData.slice((currentPage-1) * pageSize ,currentPage * pageSize)" @selection-change="handleSelectionChange" size="mini" :height="tableHeight" border style="width: 100%">

            <el-table-column align="center" type="selection" width="43"></el-table-column>
            <el-table-column align="center" prop="foodName" label="食物名字" ></el-table-column>
            <el-table-column align="center" :formatter="typeFormat" prop="foodType" label="食物类型" ></el-table-column>
            <el-table-column align="center" prop="foodTag" label="食物标签" ></el-table-column>
            <el-table-column align="center" prop="foodPrice" label="食物价格" ></el-table-column>
            <el-table-column align="center" :formatter="steamFormat" prop="isSteam" label="是否清蒸" show-overflow-tooltip></el-table-column>
            <el-table-column label="操作" width="300" align="center">
                <template slot-scope="scope">
                    <el-button @click="details(scope.row.id)" type="primary" size="mini">详情</el-button>
                    <el-button @click="findOne(scope.row.id)" type="success" size="mini">编辑</el-button>
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
                :total="foodData.length"
        ></el-pagination>

        <el-dialog title="新增食物表" :visible.sync="addOneFoodVisible" width="50%">
            <el-form size="mini" ref="addOne" style="text-align:left" :model="addOne" :inline="true" label-width="100px">
                <el-form-item label="食物名称">
                    <el-input v-model="addOne.foodName" placeholder="请输入食物名称"></el-input>
                </el-form-item>
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
                <el-form-item label="食物标签">
                    <el-input v-model="addOne.foodTag" placeholder="请输入食物标签"></el-input>
                </el-form-item>
                <br />
                <el-form-item label="食物价格">
                    <el-input v-model="addOne.foodPrice" placeholder="请输入食物价格"></el-input>
                </el-form-item>
                <br />
                <el-form-item label="食物图片">
                    <el-input v-model="addOne.foodPic" placeholder="请输入食物图片"></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="是否清蒸">
                    <el-radio-group v-model="addOne.isSteam">
                        <el-radio :label="0">否</el-radio>
                        <el-radio :label="1">是</el-radio>
                    </el-radio-group>

                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="addOneFoodVisible = false">取 消</el-button>
        <el-button @click="addOneSubmit()" type="primary" >确 定</el-button>
      </span>
        </el-dialog>

        <el-dialog title="食物详情表" :visible.sync="detailFoodVisible" width="50%">
            <el-form size="mini" ref="foodDetail" style="text-align:left" :model="foodDetail" :inline="true" label-width="100px" disabled>
                <el-form-item label="食物名称">
                    <el-input v-model="foodDetail.foodName" placeholder="请输入食物名称" ></el-input>
                </el-form-item>
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

            </el-form >
            <span slot="footer" class="dialog-footer">
        <el-button @click="detailFoodVisible = false">取 消</el-button>
      </span>
        </el-dialog>

        <el-dialog title="修改食物表" :visible.sync="updateFoodVisible" width="50%">
            <el-form size="mini" ref="updateFoodData" style="text-align:left" :model="updateFoodData" :inline="true" label-width="100px">
                <el-form-item label="食物名称">
                    <el-input v-model="updateFoodData.foodName" placeholder="请输入食物名称" ></el-input>
                </el-form-item>
                <el-form-item label="食物类型">
                    <el-radio-group v-model="updateFoodData.foodType">
                        <el-radio :label="0">大荤</el-radio>
                        <el-radio :label="1">小荤</el-radio>
                        <el-radio :label="2">素菜</el-radio>
                        <el-radio :label="3">甜点</el-radio>
                        <el-radio :label="4">水果</el-radio>
                        <el-radio :label="5">套餐</el-radio>
                    </el-radio-group>

                </el-form-item>
                <el-form-item label="食物标签">
                    <el-input v-model="updateFoodData.foodTag" placeholder="请输入食物标签" ></el-input>
                </el-form-item>
                <el-form-item label="食物价格">
                    <el-input v-model="updateFoodData.foodPrice" placeholder="请输入食物价格" ></el-input>
                </el-form-item>
                <el-form-item label="食物图片">
                    <el-input v-model="updateFoodData.foodPic" placeholder="请输入食物图片" ></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="是否清蒸">
                    <el-radio-group v-model="updateFoodData.isSteam">
                        <el-radio :label="0">否</el-radio>
                        <el-radio :label="1">是</el-radio>
                    </el-radio-group>

                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="updateFood()" type="primary" >确 定</el-button>
                <el-button @click="updateFoodVisible = false">取 消</el-button>
              </span>
        </el-dialog>

    </el-main>
</template>

<script>
    export default {
        data() {
            return {
                mode: {},
                pageSize: 10,
                currentPage: 1,
                //搜索框数据绑定
                findFoodDate: {
                    foodName: "",
                },
                // 表格高度
                tableHeight: 0,
                // 表格数据
                tableChecked: [],//选中显示的值
                ids:[],//批量删除id
                user: "",
                foodData: [],
                addOneFoodVisible: false,
                detailFoodVisible: false,
                updateFoodVisible: false,
                addOne: {
                    foodPic: "mifan.jpg"
                },
                foodDetail: {
                },
                updateFoodData: {

                },
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

            addOneFood(){
                this.addOneFoodVisible = true;
            },
            typeFormat(row) {
                if (row.foodType == 0) {
                    return "大荤";
                } else if (row.foodType == 1) {
                    return "小荤";
                } else if (row.foodType == 2){
                    return "素菜";
                } else if (row.foodType == 3){
                    return "甜点";
                } else if (row.foodType == 4){
                    return "水果";
                } else if (row.foodType == 5){
                    return "套餐";
                }
            },
            steamFormat(row){
                if (row.isSteam == 0) {
                    return "否";
                }else {
                    return "是";
                }
            },

            getFoodList(){
                this.$axios({
                    method: "post",
                    url: "api/food/foodList",

                }).then((res) => {
                    // console.log(res)
                    if (res.data.code == 200){

                        this.foodData = res.data.data
                        //console.log(this.foodData)
                    }

                })
            },
            findFood(foodName){
                this.$axios({
                    method: "post",
                    url: "api/food/selectByLike",
                    params: {
                        foodName: foodName,
                    }
                }).then((res) => {
                    //console.log(res)
                    this.findFoodDate.foodName = "";
                    if (res.data.code == 200){
                        this.$message({
                            type: "success",
                            duration: 1000,
                            message: res.data.msg
                        })
                        this.foodData = res.data.data
                        console.log(this.foodData)

                    } else {
                        this.$message({
                            type: "error",
                            duration: 1000,
                            message: res.data.msg
                        })
                    }
                })
            },
            addOneSubmit(){
                //console.log(this.addOne);
                this.$refs.addOne.validate((valid) => {
                    if(valid){
                        var data = new FormData();
                        data.append('foodName',this.addOne.foodName);
                        data.append('foodType',this.addOne.foodType);
                        data.append('foodTag',this.addOne.foodTag);
                        data.append('foodPrice',this.addOne.foodPrice);
                        data.append('foodPic',this.addOne.foodPic);
                        data.append('isSteam',this.addOne.isSteam);

                        //console.log(data);
                        this.$axios({
                            method: "post",
                            url: "api/food/addOneFood",
                            data: data
                        }).then((result) => {
                            if (result.data.code == 200) {
                                this.$message({
                                    type: "success",
                                    duration: 1000,
                                    message: result.data.msg,
                                });
                                this.addOneFoodVisible = false;
                                this.$refs.addOne.resetFields(); // 刷新表单
                                this.getFoodList();
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
            deleteOne(foodId){
               // console.log(foodId)
                this.$axios({
                    method: "post",
                    url: "api/food/delOneFood",
                    params: {
                        foodId: foodId,
                    }
                }).then((result) => {
                    if (result.data.code == 200) {
                        this.$message({
                            type: "success",
                            duration: 1000,
                            message: result.data.msg,
                        });
                        this.getFoodList();
                    } else {
                        this.$message({
                            type: "error",
                            duration: 1000,
                            message: result.data.msg,
                        });
                    }
                });
            },
            deleteList(){
                //console.log(this.tableChecked);
                this.$confirm('是否删除所选信息?','提示',{
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(()=>{
                    this.tableChecked.forEach(id =>{
                        this.ids.push(id.id);
                    });
                    //console.log(this.ids);
                    this.$axios({
                        method: "delete",
                        url: "api/food/delete",
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        },
                        data: JSON.stringify(this.ids),
                    }).then((result) => {
                        if (result.data.code == 200) {
                            this.$message({
                                type: "success",
                                duration: 1000,
                                message: result.data.msg,
                            });
                            this.getFoodList();
                            this.ids = [];

                        } else {
                            this.$message({
                                type: "error",
                                duration: 1000,
                                message: result.data.msg,
                            });
                        }
                    });
                })
            },
            details(foodId){
                this.detailFoodVisible = true;
                //console.log(foodId)
                this.$axios({
                    method: "get",
                    url: "api/food/findOneFood",
                    params: {
                        foodId: foodId,
                    }
                }).then((result) =>{
                    this.foodDetail = result.data.data;
                     //console.log(this.updateCheckin);
                })

            },
            findOne(foodId){
                this.updateFoodVisible = true;
                //console.log(foodId)
                this.$axios({
                    method: "get",
                    url: "api/food/findOneFood",
                    params: {
                        foodId: foodId,
                    }
                }).then((result) =>{
                    this.updateFoodData = result.data.data;
                    //console.log(this.updateCheckin);
                })
            },
            updateFood(){
                this.$refs.updateFoodData.validate((valid) => {
                    if(valid){
                        var data = new FormData();
                        data.append('foodName',this.updateFoodData.foodName);
                        data.append('foodType',this.updateFoodData.foodType);
                        data.append('foodTag',this.updateFoodData.foodTag);
                        data.append('foodPrice',this.updateFoodData.foodPrice);
                        data.append('foodPic',this.updateFoodData.foodPic);
                        data.append('isSteam',this.updateFoodData.isSteam);
                        data.append('id',this.updateFoodData.id);
                        console.log(data)
                        this.$axios({
                            method: "post",
                            url: "api/food/updateOneFood",
                            data: data
                        }).then((result) => {
                            console.log(result);
                            if (result.data.code == 200) {
                                this.$message({
                                    type: "success",
                                    duration: 1000,
                                    message: result.data.msg,
                                });
                                this.updateFoodVisible = false;
                                this.getFoodList();
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
            }
        },
        created: function () {
            this.getFoodList();
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