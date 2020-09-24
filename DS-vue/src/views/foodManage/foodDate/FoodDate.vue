<template>


    <el-main >
        <el-form size="mini" :model="findUserDate" label-width="80px">
            <el-row>
                <el-col :span="5">
                    <el-form-item style="font-size: 10px" label="档案号:">
                        <el-input v-model="findUserDate.recordId" placeholder="请输入患者档案号"></el-input>
                    </el-form-item>
                </el-col>
                <el-button @click="findUser(findUserDate.recordId)"  style="margin-left: 20px;" size="mini" type="primary" icon="el-icon-search">查询</el-button>
            </el-row>
        </el-form>
        <el-carousel height="360px" width="1107px"  :interval="3000" direction="vertical" v-if="user!=''" >
           <el-carousel-item v-for="(date,index) in week" :key="index"  style="text-align: center;">

               <h2 style="margin: 5px;">这是本周第{{index+1}}天的膳食</h2>
                <el-row style="display: flex; justify-content: center;" >
                   <div class="food-class" v-if="date[0].day == 0"  >
                           <h1>早餐</h1>
                           <el-row  style="display: flex;justify-content: center;" >
                               <el-col v-for="(a,index) in date[0].foodList" :key="index">

                                   <img style="height: 80px;width: 80px;margin: 10px" :src="'http://localhost:8282/foodDate/pic?pic='+a.foodPic"/>
                                   <div style="width: 70px;">
                                       <el-tag style="margin: 3px;" type="warning">{{a.foodTag}}</el-tag>
                                       <el-tag style="margin: 3px;" type="success" v-if="a.isSteam == 1">清蒸</el-tag>
                                   </div>

                               </el-col>
                           </el-row>
                       </div>
                   <div class="food-class" v-if="date[1].day == 1" >
                           <h1 >午餐</h1>
                           <el-row  style="display: flex;justify-content: center;" >
                               <el-col v-for="(b,index) in date[1].foodList" :key="index">
                                   <img style="height: 80px;width: 80px;margin: 10px" :src="'http://localhost:8282/foodDate/pic?pic='+b.foodPic"/>
                                   <div style="width: 70px;">
                                       <el-tag style="margin: 3px;" type="warning">{{b.foodTag}}</el-tag>
                                       <el-tag style="margin: 3px;" type="success" v-if="b.isSteam == 1">清蒸</el-tag>
                                   </div>

                               </el-col>
                           </el-row>
                       </div>
                   <div class="food-class" v-if="date[2].day == 2" >
                           <h1 >晚餐</h1>
                           <el-row  style="display: flex;justify-content: center;" >
                               <el-col v-for="(c,index) in date[2].foodList" :key="index">
                                   <img style="height: 80px;width: 80px;margin: 10px" :src="'http://localhost:8282/foodDate/pic?pic='+c.foodPic"/>
                                   <div style="width: 70px;">
                                       <el-tag style="margin: 3px;" type="warning">{{c.foodTag}}</el-tag>
                                       <el-tag style="margin: 3px;" type="success" v-if="c.isSteam == 1">清蒸</el-tag>
                                   </div>

                               </el-col>
                           </el-row>
                       </div>
               </el-row>

            </el-carousel-item>
        </el-carousel>

    </el-main>

</template>

<script>
    export default {
        data() {
            return {
                updateFoodVisible: false,
                findUserDate: {
                    recordId: "",
                },
                foodDate: [],
                user: "",
                week: [],
                sunday: [],
                monday: [],
                tuesday: [],
                wednesday: [],
                thursday: [],
                friday: [],
                saturday: [],
            }
        },
        methods: {

            findUser(recordId){
                this.$axios({
                    method: "post",
                    url: "api/foodDate/foodDateList",
                    params: {
                        recordId: recordId,
                    }
                }).then((res) => {
                    //console.log(res)
                    if (res.data.code == 200){
                        this.$message({
                            type: "success",
                            duration: 1000,
                            message: res.data.msg
                        })
                        this.foodDate = res.data.data
                        this.user = "user";
                        //console.log(this.foodDate)
                        let a = [];
                        let b = [];
                        let c = [];
                        let d = [];
                        let e = [];
                        let f = [];
                        let g = [];

                        for (let v of Object.values(this.foodDate)) {
                            if (v.date == 0){
                                //console.log(v)
                                a.push(v)
                            }
                            else if (v.date == 1){
                                //console.log(v)
                                b.push(v)
                            }else if (v.date == 2){
                                //console.log(v)
                                c.push(v)
                            }else if (v.date == 3){
                                //console.log(v)
                                d.push(v)
                            }else if (v.date == 4){
                                //console.log(v)
                                e.push(v)
                            }else if (v.date == 5){
                                //console.log(v)
                                f.push(v)
                            }else if (v.date == 6){
                                //console.log(v)
                                g.push(v)
                            }

                        }
                        //console.log(a)
                        this.sunday = a;
                        this.monday = b;
                        this.tuesday = c;
                        this.wednesday = d;
                        this.thursday = e;
                        this.friday = f;
                        this.saturday = g;
                        if (this.sunday.length > 0){
                            this.week.push(this.sunday);
                        }
                        if (this.monday.length > 0){
                            this.week.push(this.monday);
                        }
                        if(this.tuesday.length > 0){
                            this.week.push(this.tuesday);
                        }
                        if(this.wednesday.length > 0){
                            this.week.push(this.wednesday);
                        }
                        if(this.thursday.length > 0){
                            this.week.push(this.thursday);
                        }
                        if(this.friday.length > 0){
                            this.week.push(this.friday);
                        }
                        if(this.saturday.length > 0){
                            this.week.push(this.saturday);
                        }

                        //console.log(this.week)
                    } else {
                        this.$message({
                            type: "error",
                            duration: 1000,
                            message: res.data.msg
                        })
                    }
                })
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