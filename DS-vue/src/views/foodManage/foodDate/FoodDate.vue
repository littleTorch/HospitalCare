<template>



    <el-main >

        <el-header height="50px" >
            <el-form size="mini" :model="stuFrom" label-width="50px">
                <el-row>
                    <el-col :span="5">
                        <el-form-item style="font-size: 15px" label="姓名:">
                            <el-input  placeholder="请输入患者姓名"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-button  style="margin-left: 20px;" size="mini" type="primary" icon="el-icon-search">查询</el-button>
                </el-row>
            </el-form>
        </el-header>

        <el-carousel height="380px"  :interval="3000" direction="vertical" v-if="user!=''" >
           <el-carousel-item v-for="item in 7" :key="item" style="text-align: center;">

               <h4 style="margin: 5px;">这是本周第{{item}}天的膳食</h4>
               <el-button @click="updateNowFood" style="margin-top: 10px;" size="mini" type="primary" icon="el-icon-edit-outline">修改当前膳食数据</el-button>
               <el-row style="display: flex; justify-content: center;" >
                       <div class="food-class"  >
                           <h1 >早餐</h1>
                           <el-row style="display: flex;justify-content: center;" >
                               <el-col  >
                                   <img style="height: 80px;width: 80px;margin: 10px" src=""/>
                                   <div style="width: 70px;">
                                       <el-tag style="margin: 3px;" type="warning">素菜</el-tag>
                                       <el-tag style="margin: 3px;" type="success">多脂</el-tag>
                                       <el-tag style="margin: 3px;" type="info">清蒸</el-tag>
                                   </div>

                               </el-col>
                               <el-col  >
                                   <img style="height: 80px;width: 80px;margin: 10px" src=""/>
                                   <div style="width: 70px;">
                                       <el-tag style="margin: 3px;" type="warning">素菜</el-tag>
                                       <el-tag style="margin: 3px;" type="success">多脂</el-tag>
                                       <el-tag style="margin: 3px;" type="info">清蒸</el-tag>
                                   </div>

                               </el-col>
                               <el-col  >
                                   <img style="height: 80px;width: 80px;margin: 10px" src=""/>
                                   <div style="width: 70px;">
                                       <el-tag style="margin: 3px;" type="warning">素菜</el-tag>
                                       <el-tag style="margin: 3px;" type="success">多脂</el-tag>
                                       <el-tag style="margin: 3px;" type="info">清蒸</el-tag>
                                   </div>

                               </el-col>
                           </el-row>
                       </div>
                       <div class="food-class"  >
                           <h1 >午餐</h1>
                           <el-row style="display: flex;justify-content: center;" >
                               <el-col  >
                                   <img style="height: 80px;width: 80px;margin: 10px" src=""/>
                                   <div style="width: 70px;">
                                       <el-tag style="margin: 3px;" type="warning">素菜</el-tag>
                                       <el-tag style="margin: 3px;" type="success">多脂</el-tag>
                                       <el-tag style="margin: 3px;" type="info">清蒸</el-tag>
                                   </div>

                               </el-col>
                               <el-col  >
                                   <img style="height: 80px;width: 80px;margin: 10px" src=""/>
                                   <div style="width: 70px;">
                                       <el-tag style="margin: 3px;" type="warning">素菜</el-tag>
                                       <el-tag style="margin: 3px;" type="success">多脂</el-tag>
                                       <el-tag style="margin: 3px;" type="info">清蒸</el-tag>
                                   </div>

                               </el-col>
                               <el-col  >
                                   <img style="height: 80px;width: 80px;margin: 10px" src=""/>
                                   <div style="width: 70px;">
                                       <el-tag style="margin: 3px;" type="warning">素菜</el-tag>
                                       <el-tag style="margin: 3px;" type="success">多脂</el-tag>
                                       <el-tag style="margin: 3px;" type="info">清蒸</el-tag>
                                   </div>

                               </el-col>
                           </el-row>
                       </div>
                       <div class="food-class"  >
                           <h1  >晚餐</h1>
                           <el-row style="display: flex;justify-content: center;" >
                               <el-col  >
                                   <img style="height: 80px;width: 80px;margin: 10px" src=""/>
                                   <div style="width: 70px;">
                                       <el-tag style="margin: 3px;" type="warning">素菜</el-tag>
                                       <el-tag style="margin: 3px;" type="success">多脂</el-tag>
                                       <el-tag style="margin: 3px;" type="info">清蒸</el-tag>
                                   </div>

                               </el-col>
                               <el-col  >
                                   <img style="height: 80px;width: 80px;margin: 10px" src=""/>
                                   <div style="width: 70px;">
                                       <el-tag style="margin: 3px;" type="warning">素菜</el-tag>
                                       <el-tag style="margin: 3px;" type="success">多脂</el-tag>
                                       <el-tag style="margin: 3px;" type="info">清蒸</el-tag>
                                   </div>

                               </el-col>
                               <el-col  >
                                   <img style="height: 80px;width: 80px;margin: 10px" src=""/>
                                   <div style="width: 70px;">
                                       <el-tag style="margin: 3px;" type="warning">素菜</el-tag>
                                       <el-tag style="margin: 3px;" type="success">多脂</el-tag>
                                       <el-tag style="margin: 3px;" type="info">清蒸</el-tag>
                                   </div>

                               </el-col>
                           </el-row>
                       </div>
               </el-row>

            </el-carousel-item>
        </el-carousel>

        <el-dialog title="修改当前膳食数据" :visible.sync="updateFoodVisible" width="40%">
            <el-form
                    class="updateFood"
                    ref="updateFood"
                    size="mini"
                    style="text-align:center"
                    :model="foodData"
                    :inline="true"
                    label-width="100px">

            </el-form>
            <span slot="footer" class="dialog-footer">
            <el-button @click="updateFoodVisible = false">取 消</el-button>
            <el-button type="primary" >确 定</el-button>
          </span>
        </el-dialog>

    </el-main>

</template>

<script>
    export default {
        data() {
            return {
                updateFoodVisible: false,
                foodData: [],
                user: "",
            }
        },
        methods: {
            updateNowFood(){
                this.updateFoodVisible = true;
            },

        },


    }
</script>

<style scoped>


    .el-carousel__item:nth-child(2n) {
        background-color: #eee;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #eee;
    }

    .food-class {
        font-size: 20px;
        margin: 10px;
        text-align: center;

    }

</style>