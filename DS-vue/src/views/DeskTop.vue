<template>
  <div class="dashboard-editor-container">
    <el-row :gutter="20" style="margin: 20px">
      <el-carousel :interval="4000" type="card" height="300px" v-if="user!='admin'">
        <el-carousel-item v-for="item in 7" :key="item" >
          <div class="food-class" span="4">
            <h1 style="" >早餐</h1>
            <img style="height:100px;" src="../assets/images/zc.jpg"/>
          </div>
          <div class="food-class" span="4">
            <h1 >午餐</h1>
            <img style="height:100px;" src="../assets/images/lunch.jpg"/>
          </div>
          <div class="food-class" span="4">
            <h1 >晚餐</h1>
            <img style="height:100px;" src="../assets/images/wc.jpg"/>
          </div>
          <h4 style="margin-left:20px;">今天是本周的第{{item}}天！
            健康提醒：三餐按时搭配吃，疾病好得快....</h4>
        </el-carousel-item>
    </el-carousel>
    </el-row>

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;" v-if="user=='admin'">
      <div id="myChart" style="width: 100%;height: 350px"/>
    </el-row>
  </div>
</template>
<script>
  export default {
    data() {
          return {
            user: "user",
          }
       },
    methods: {

     
    },
    mounted(){
    },
  }
</script>
<style>
 
   .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #eee;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #eee;
  }

  .food-class {
    font-size: 20px;
    display: inline-block;
    margin: 20px;
    text-align: center;
    
  }

</style>