<template>
  <div class="dashboard-editor-container">
    <el-row :gutter="20" style="margin: 20px">
      <el-carousel :interval="2000" type="card">
        <el-carousel-item  style="text-align: center;width: 600px">
          <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
            <div id="myChart" style="width: 500px;height: 300px"/>
          </el-row>
        </el-carousel-item>

        <el-carousel-item  style="text-align: center;width: 600px">
          <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
            <div id="myChart2" style="width: 500px;height: 300px"/>
          </el-row>
        </el-carousel-item>

    </el-carousel>
    </el-row>

  </div>
</template>
<script>
  export default {
    data() {
          return {
            user: "user",
            e1data: {},
          }
       },
    methods: {
      drawLine1() {
        //console.log(index);
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.$echarts.init(document.getElementById('myChart'));

          let arr = [];
          this.$axios({
            method: "get",
            url: "api/echart/echart1"
          }).then((result) => {
            this.e1data = result.data;

            let p1 = this.e1data.p1;
            let p2 = this.e1data.p2;
            arr.push({value: p1, name: '未入宿床位数量'});
            arr.push({value: p2, name: '入宿床位数量'});
            myChart.setOption({
              title: {
                text: '住宿床位情况',
                left: 'center'
              },
              tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
              },
              legend: {
                orient: 'vertical',
                left: 'left',
                data: ['未入宿床位数量', '入宿床位数量']
              },
              series: [
                {
                  name: '床位情况',
                  type: 'pie',
                  radius: '75%',
                  center: ['50%', '50%'],
                  label: {
                    show: true,
                    formatter: '{b}:{d}%',
                    position: 'outside'
                  },
                  data: arr,
                  emphasis: {
                    itemStyle: {
                      shadowBlur: 10,
                      shadowOffsetX: 0,
                      shadowColor: 'rgba(30, 30, 0, 0.5)'
                    }
                  }
                }
              ]
            }, true);
          });

      },
      drawLine2() {
        //console.log(index);
        // 基于准备好的dom，初始化echarts实例
        let myChart2 = this.$echarts.init(document.getElementById('myChart2'));

        // 绘制图表

          let arr = [];
          this.$axios({
            method: "get",
            url: "api/echart/echart2"
          }).then((result) => {
            this.e1data = result.data;

            let p1 = this.e1data.p1;
            let p2 = this.e1data.p2;
            console.log(p1+" "+p2)
            arr.push({value: p1, name: '入宿客户'});
            arr.push({value: p2, name: '未入宿客户'});
            myChart2.setOption({
              title: {
                text: '住宿客户情况',
                left: 'center'
              },
              tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
              },
              legend: {
                orient: 'vertical',
                left: 'left',
                data: ['入宿客户', '未入宿客户']
              },
              series: [
                {
                  name: '入宿客户情况',
                  type: 'pie',
                  radius: '75%',
                  center: ['50%', '50%'],
                  label: {
                    show: true,
                    formatter: '{b}:{d}%',
                    position: 'outside'
                  },
                  data: arr,
                  emphasis: {
                    itemStyle: {
                      shadowBlur: 10,
                      shadowOffsetX: 0,
                      shadowColor: 'rgba(30, 30, 0, 0.5)'
                    }
                  }
                }
              ]
            }, true);
          });

      }

    },
    mounted(){
      this.drawLine1();
      this.drawLine2();
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