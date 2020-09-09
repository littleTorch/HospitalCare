<template>
  <div class="dashboard-editor-container">
    <el-row :gutter="20" style="margin-bottom: 20px">
      <el-col :span="6">
        <el-card :body-style="{ padding: '10px' }" shadow="hover" @click.native="drawLine('1')">
          <svg t="1597053194712" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2485" width="64" height="64"><path d="M512.07 592.01c-132.31 0-240-107.69-240-240s107.69-240 240-240 240 107.69 240 240-107.68 240-240 240z m0-384c-79.38 0-144 64.62-144 144s64.62 144 144 144 144-64.62 144-144-64.62-144-144-144z" fill="#131414" p-id="2486"></path><path d="M832.07 912.01c-26.5 0-48-21.5-48-48 0-150-122-272-272-272s-272 122-272 272c0 26.5-21.5 48-48 48s-48-21.5-48-48c0-202.94 165.06-368 368-368s368 165.06 368 368c0 26.5-21.5 48-48 48z" fill="#131414" p-id="2487"></path></svg>
          <div style="padding: 14px;float: right">
            <div>学生住宿情况</div>
            <div style="color: red;text-align: center;font-size: 21px;font-weight: bold;">
              <countTo :startVal='startVal' :endVal='endVal1' :duration='3000'></countTo>
            </div>
          </div>

        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card :body-style="{ padding: '10px' }" shadow="hover" @click.native="drawLine('2')">
          <svg t="1597054067361" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3641" width="64" height="64"><path d="M238.933333 187.733333H119.466667a17.066667 17.066667 0 0 0-17.066667 17.066667v34.133333h136.533333v-51.2z m68.266667 0v51.2h136.533333v-51.2h-136.533333z m204.8 0v51.2h136.533333v-34.133333a17.066667 17.066667 0 0 0-17.066666-17.066667h-119.466667z m136.533333 665.6H102.4v85.333334H34.133333V204.8a85.333333 85.333333 0 0 1 85.333334-85.333333h512a85.333333 85.333333 0 0 1 85.333333 85.333333v34.133333h238.933333a34.133333 34.133333 0 0 1 34.133334 34.133334v665.6h-68.266667v-85.333334H716.8v85.333334h-68.266667v-85.333334z m68.266667-68.266666h204.8V307.2H716.8v68.266667h204.8v68.266666H716.8v68.266667h204.8v68.266667H716.8v68.266666h204.8v68.266667H716.8v68.266667z m-68.266667-102.4V409.6H102.4v273.066667h546.133333z m0-341.333334v-34.133333H102.4v34.133333h546.133333z m0 443.733334v-34.133334H102.4v34.133334h546.133333z" fill="#222222" p-id="3642"></path><path d="M102.4 614.4h204.8a17.066667 17.066667 0 0 1 17.066667 17.066667v34.133333a17.066667 17.066667 0 0 1-17.066667 17.066667H102.4v-68.266667z" fill="#FFDF32" p-id="3643"></path></svg>
          <div style="padding: 14px;float: right">
            <div>宿舍数量</div>
            <div style="color: aqua;text-align: center;font-size: 21px;font-weight: bold;">
              <countTo :startVal='startVal' :endVal='endVal2' :duration='3000'></countTo>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card :body-style="{ padding: '10px' }" shadow="hover" @click.native="drawLine('3')">
          <svg t="1597054328239" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5142" width="64" height="64"><path d="M585.6428032 285.25745493c12.77842773 84.4005376 78.9184512 150.7098624 163.2927744 163.69800534-4.3941888 0.36590933-8.82005333 0.54941013-13.27104 0.54941013a153.27341227 153.27341227 0 0 1-31.3065472-3.2473088l-38.76017493-8.1002496-28.00790187 28.00790187-374.8626432 374.90633386c-19.1070208 19.1463424-50.12084053 19.17801813-69.26390613 0.0688128-19.14743467-19.1070208-19.17801813-50.11647147-0.0688128-69.26390613L568.20804267 397.01490347l28.01117866-28.0068096-8.1002496-38.76563627a152.96211627 152.96211627 0 0 1-2.65857706-45.03197013m128.0049152-208.2799616c-4.24564053 0.0131072-8.4836352 0.34952533-12.67357014 1.0092544A223.42642347 223.42642347 0 0 0 516.63121067 345.0732544L141.81444267 719.93043627c-46.32958293 47.92101547-45.6884224 124.14266027 1.4450688 171.27615146 47.13567573 47.13130667 123.35295147 47.77683627 171.2750592 1.4450688L689.39502933 517.78901333a226.44217173 226.44217173 0 0 0 46.31210667 4.5744128c110.2020608 0.2392064 204.06490453-80.019456 220.95243947-188.91844266 4.5776896-30.43273387-7.41102933-47.77683627-23.42912-47.77683627a34.10274987 34.10274987 0 0 0-23.3897984 11.4393088l-44.7086592 44.7152128c-48.17660587 44.1024512-122.5162752 42.51757227-168.7748608-3.588096-46.2553088-46.1111296-48.07502507-120.44752213-4.1254912-168.7617536 43.94625707-48.31860053 20.04200107-19.95461973 44.70975146-44.7152128 22.15007573-22.15007573 11.12036693-47.86967893-23.56893013-47.86967893l0.09284267 0.0425984z m0 0" fill="#006569" p-id="5143"></path><path d="M228.12972373 765.6964096c-19.43033173 0.00764587-36.1463808 13.73525333-39.93545386 32.78984533-3.7879808 19.05568427 6.4126976 38.1321216 24.35863893 45.56936534 17.9503104 7.438336 38.65204053 1.16217173 49.4501888-14.98699094 10.79814827-16.15353173 8.68898133-37.68210773-5.04081067-51.4260992a40.55913813 40.55913813 0 0 0-28.8325632-11.94612053z m-11.9013376-460.71589547l141.1383296 141.18638934 57.57228374-57.6192512L273.84763733 247.40932267l-31.6243968-60.40999254-100.77252266-71.9880192L83.83146667 172.58359467l71.9880192 100.81949013 60.40890026 31.57742933z m472.29392214 299.4864128c-7.95170133-7.9429632-20.83170987-7.9429632-28.78450347 0l-86.49550507 86.40375467c-7.9429632 7.95170133-7.9429632 20.83607893 0 28.78450347l203.79183787 203.79511466a81.46670933 81.46670933 0 0 0 79.16202667 22.08453974 81.46343253 81.46343253 0 0 0 36.0251392-137.27716694L688.52230827 604.46692693z m0 0" fill="#006569" p-id="5144"></path></svg>          <div style="padding: 14px;float: right">
            <div>新增问题设施</div>
            <div style="color: yellow;text-align: center;font-size: 21px;font-weight: bold;">
              <countTo :startVal='startVal' :endVal='endVal3' :duration='3000'></countTo>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card :body-style="{ padding: '10px' }" shadow="hover" @click.native="drawLine('4')" >
          <svg t="1597054001565" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3139" width="64" height="64"><path d="M592.6 936.22a185.44 185.44 0 0 1-63.74-11c-18-6.62-32.55-20.39-40.94-38.78-10.07-22.07-10.74-49.33-1.78-72.93 19.95-52.6 29.39-100.16 28-141.33-1.14-35.66-19.49-69.38-49.06-90.18-27.23-19.21-67-46.34-101.83-66.47-28.85-16.68-47.52-41.28-54-71.15-6-27.84-1.63-59.3 13.1-93.5a247.59 247.59 0 0 1 22.18-40.67c73.78-118.22 254-111.24 269.88-110.37 124.76 2 205.34 102.8 242.31 162.82A278.33 278.33 0 0 1 894.6 465c9.89 61.62 7.79 122-6.24 179.54a442.69 442.69 0 0 1-56 131.33c-44.2 70-94 107.16-96.09 108.7l-0.11 0.08c-39.11 28.58-79.55 45.48-120.2 50.24a199.31 199.31 0 0 1-23.36 1.33zM397.35 346.31c-9.38 13.32-32.17 54.38-25.53 84.58 2.69 12.21 10.14 21.48 23.46 29.18 36.89 21.32 78.38 49.61 106.69 69.58a181.53 181.53 0 0 1 54.53 61.17A178.71 178.71 0 0 1 578.14 670c1.63 49.72-9.14 105.62-32.14 166.19-2.88 7.6-2.81 17.11 0.18 23.65 1.92 4.22 4.07 5 4.77 5.27 25.7 9.44 79 17.81 147.38-32.11 1-0.72 43.8-33.27 81.25-93.46 49.74-79.94 67.19-168.91 51.85-264.44a214.48 214.48 0 0 0-29.17-78.92C772.08 347.24 707.4 265 612.86 263.77l-1.59-0.06c-1.53-0.09-157.77-8.3-212.77 80.86l-0.56 0.91zM225.26 335.77a102 102 0 1 1 102-102 102.11 102.11 0 0 1-102 102z m0-140a38 38 0 1 0 38 38 38 38 0 0 0-38-38z" fill="#231815" p-id="3140"></path><path d="M421.26 134.77m-50 0a50 50 0 1 0 100 0 50 50 0 1 0-100 0Z" fill="#231815" p-id="3141"></path><path d="M592.26 113.77m-50 0a50 50 0 1 0 100 0 50 50 0 1 0-100 0Z" fill="#231815" p-id="3142"></path><path d="M763.26 153.77m-50 0a50 50 0 1 0 100 0 50 50 0 1 0-100 0Z" fill="#231815" p-id="3143"></path></svg>          <div style="padding: 14px;float: right">
            <div>新增访客</div>
            <div style="color: green;text-align: center;font-size: 21px;font-weight: bold;">
              <countTo :startVal='startVal' :endVal='endVal4' :duration='3000'></countTo>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <div id="myChart" style="width: 100%;height: 350px"/>
    </el-row>
  </div>
</template>
<script>
  import countTo from "vue-count-to";
  export default {
    components: { countTo },
    data () {
      return {
        lineChartData: 500,
        startVal: 0,
        endVal1: 0,
        endVal2: 0,
        endVal3: 0,
        endVal4: 0,
        e1data: {},
        e2data: {},
        e3data: [],
        e4data: [],
        e4data2: [],
      }
    },
    methods: {

      drawLine(index) {
        //console.log(index);
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.$echarts.init(document.getElementById('myChart'));
        // 绘制图表
        if (index==1){
          let arr = [];
          this.$axios({
            method: "get",
            url: "api/echart/echart1"
          }).then((result) => {
            this.e1data = result.data;

              let p1 = this.e1data.p1;
              let p2 = this.e1data.p2;
              let p3 = this.e1data.p3;
              arr.push({value: p1, name: '未入宿学生数量'});
              arr.push({value: p2, name: '入宿学生数量'});
              arr.push({value: p3, name: '退宿学生数量'});
              myChart.setOption({
                  title: {
                      text: '学生住宿情况',
                      left: 'center'
                  },
                  tooltip: {
                      trigger: 'item',
                      formatter: '{a} <br/>{b} : {c} ({d}%)'
                  },
                  legend: {
                      orient: 'vertical',
                      left: 'left',
                      data: ['未入宿学生数量','入宿学生数量','退宿学生数量']
                  },
                  series: [
                      {
                          name: '宿舍情况',
                          type: 'pie',
                          radius: '75%',
                          center: ['50%', '50%'],
                          label: {
                              show: true,
                              formatter: '{b}:{d}%',
                              position:'outside'
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
              },true);
          });

        }
        else if(index==2) {
            let arr = [];
            this.$axios({
                method: "get",
                url: "api/echart/echart2"
            }).then((result) => {
                this.e2data = result.data;
                //console.log(this.e1data.p1);
                let p1 = this.e2data.p1;
                let p2 = this.e2data.p2;
                let p3 = this.e2data.p3;
                arr.push({value: p1, name: '空宿舍数量'});
                arr.push({value: p2, name: '满员宿舍数量'});
                arr.push({value: p3, name: '未满员宿舍数量'});

                myChart.setOption({
                    title: {
                        text: '宿舍数量',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['空宿舍数量','未满员宿舍数量','满员宿舍数量']
                    },
                    series: [
                        {
                            name: '宿舍情况',
                            type: 'pie',
                            radius: '75%',
                            center: ['50%', '50%'],
                            label: {
                                show: true,
                                formatter: '{b}:{d}%',
                                position:'inside'
                            },
                            data: arr,
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                },true);
            });

        }
        else if(index==3) {
          let arr = [];
          this.$axios({
            method: "get",
            url: "api/echart/echart3"
          }).then((result) => {
            this.e3data = result.data;
            //console.log(this.e1data.p1);
            let p1 = this.e3data.p1;
            let p2 = this.e3data.p2;
            arr.push({value: p1, name: '已解决的问题设施'});
            arr.push({value: p2, name: '未解决的问题设施'});

            myChart.setOption({
              title: {
                text: '新增问题设施',
                left: 'center'
              },
              tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
              },
              legend: {
                orient: 'vertical',
                left: 'left',
                data: ['已解决的问题设施','未解决的问题设施']
              },
              series: [
                {
                  name: '问题设施详情',
                  type: 'pie',
                  radius: '75%',
                  center: ['50%', '50%'],
                  label: {
                    show: true,
                    formatter: '{b}:{d}%',
                    position:'inside'
                  },
                  data: arr,
                  emphasis: {
                    itemStyle: {
                      shadowBlur: 10,
                      shadowOffsetX: 0,
                      shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                  }
                }
              ]
            },true);
          });

        }
        else if(index==4){
            this.$axios({
                method: "get",
                url: "api/echart/echart4"
            }).then((result) => {
                this.e4data = result.data.p1;
                this.e4data2 = result.data.p2;
                myChart.setOption({
                    title: {
                        text: '新增访客',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c}'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',              //设置区分（哪条线属于什么）
                        data:['新增访客']

                    },
                    color: [ '#80fa3f'],       //设置区分（每条线是什么颜色，和 legend 一一对应）
                    xAxis: {                //设置x轴
                        type: 'category',
                        boundaryGap: false,     //坐标轴两边不留白
                        data: this.e4data,
                        name: '时间',           //X轴 name
                        nameTextStyle: {        //坐标轴名称的文字样式
                            color: '#000',
                            fontSize: 16,
                            padding: [0, 0, 0, 20]
                        },
                        axisLine: {             //坐标轴轴线相关设置。
                            lineStyle: {
                                color: '#000',
                            }
                        }
                    },
                    yAxis: {
                        name: '访客数量',
                        nameTextStyle: {
                            color: '#000',
                            fontSize: 16,
                            padding: [0, 0, 10, 0]
                        },
                        axisLine: {
                            lineStyle: {
                                color: '#000',
                            }
                        },
                        type: 'value'
                    },
                    series: [
                        {
                            name: '新增访客',
                            data: this.e4data2,
                            type: 'line',
                            lineStyle: {
                                normal: {
                                    color: '#80fa3f',
                                }
                            },
                        }
                    ]

                },true);
            });

        }

      },
      countto(){

        this.$axios({
          method: "get",
          url: "api/echart/echart1"
        }).then((result) => {
          this.e1data = result.data;
          //console.log(this.e1data.p1);
          let p1 = this.e1data.p1;
          let p2 = this.e1data.p2;
          let p3 = this.e1data.p3;
          this.endVal1 = p1+p2+p3;
        });

        this.$axios({
          method: "get",
          url: "api/echart/echart2"
        }).then((result) => {
          this.e2data = result.data;
          //console.log(this.e1data.p1);
          let p1 = this.e2data.p1;
          let p2 = this.e2data.p2;
          let p3 = this.e2data.p3;
          this.endVal2 = p1+p2+p3;

        });

        this.$axios({
          method: "get",
          url: "api/echart/echart3"
        }).then((result) => {
          this.e3data = result.data;
          //console.log(this.e1data.p1);
          let p1 = this.e3data.p1;
          let p2 = this.e3data.p2;
          this.endVal3 = p1+p2;
        });

        this.$axios({
          method: "get",
          url: "api/echart/echart4"
        }).then((result) => {
          this.e4data = result.data.p1;
          this.e4data2 = result.data.p2;
          console.log(this.e4data2);
          let a = 0;
          this.e4data2.forEach(item => {
            a = a + item;
          });
         this.endVal4 = a;

        });

      },
    },
    mounted(){
      this.drawLine(1);
      this.countto();
    },
  }
</script>
<style>
  .dashboard-editor-container {
    padding: 32px;
    background-color: rgb(240, 242, 245);
    position: relative;
  }
  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }


  @media (max-width:1024px) {
    .chart-wrapper {
      padding: 8px;
    }
  }
</style>