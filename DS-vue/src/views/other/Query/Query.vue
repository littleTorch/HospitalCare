<template>
    <div class="dashboard-editor-container">
        <el-row :gutter="20" style="margin-bottom: 20px">
            <el-col :span="8 ">
                <el-card :body-style="{ padding: '10px' }" shadow="hover" @click.native="drawLine('1')">
                    <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 1024 1024" width="64" height="64" t="1597561060428" p-id="612" version="1.1">
                        <path fill="" d="M 869.2 844.1 H 154.8 c -10.4 0 -18.8 -8.4 -18.8 -18.8 s 8.4 -18.8 18.8 -18.8 h 12.5 v -564 c 0 -34.6 28.1 -62.7 62.7 -62.7 h 313.3 c 34.6 0 62.7 28.1 62.7 62.7 v 125.3 h 188 c 34.6 0 62.7 28.1 62.7 62.7 v 376 h 12.5 c 10.4 0 18.8 8.4 18.8 18.8 s -8.4 18.8 -18.8 18.8 Z M 342.8 298.9 h -87.7 v 87.7 h 87.7 v -87.7 Z m 0 178.6 h -87.7 v 87.7 h 87.7 v -87.7 Z m 0 178.6 h -87.7 v 87.7 h 87.7 v -87.7 Z m 175.5 -357.2 h -87.7 v 87.7 h 87.7 v -87.7 Z m 0 178.6 h -87.7 v 87.7 h 87.7 v -87.7 Z m 0 178.6 h -87.7 v 87.7 h 87.7 v -87.7 Z m 263.2 -175.4 h -87.7 v 87.7 h 87.7 v -87.7 Z m 0 175.4 h -87.7 v 87.7 h 87.7 v -87.7 Z" p-id="613" />
                    </svg>
                    <div style="padding: 14px;float: right">
                        <div>房间数统计</div>
                        <div style="color: red;text-align: center;font-size: 21px;font-weight: bold;">
                            <countTo :startVal='startVal' :endVal='endVal1' :duration='3000'></countTo>
                        </div>
                    </div>

                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card :body-style="{ padding: '10px' }" shadow="hover" @click.native="drawLine('2')">
                    <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 1024 1024" width="64" height="64" t="1597563008319" p-id="1864" version="1.1"><path d="M 116.578 383.002 c 0 -53.443 43.4791 -96.9242 96.9242 -96.9242 h 217.572 c 33.7846 0 63.5722 17.3874 80.9257 43.67 c 17.3556 -26.2847 47.1411 -43.67 80.9257 -43.67 h 217.574 c 53.443 0 96.9221 43.4813 96.9221 96.9242 v 4.46348 h 24.2639 v -94.5154 c 0 -58.6507 -47.7157 -106.366 -106.366 -106.366 H 198.679 c -58.6507 0 -106.366 47.7157 -106.366 106.366 v 94.5154 h 24.2639 v -4.46348 Z M 983.263 432.726 H 40.7353 C 18.2737 432.726 0 451 0 473.461 v 236.477 c 0 22.4616 18.2737 40.7353 40.7353 40.7353 h 16.957 l -20.0358 56.5473 c -4.1751 11.781 1.99319 24.7156 13.7721 28.8886 c 2.49997 0.884214 5.05083 1.30618 7.55929 1.30618 c 9.31499 0 18.0426 -5.79722 21.3314 -15.0804 l 25.392 -71.6637 H 918.289 l 25.392 71.6637 c 3.28877 9.28318 12.0143 15.0804 21.3314 15.0804 c 2.50845 0 5.05931 -0.421963 7.55929 -1.30618 c 11.781 -4.1751 17.9472 -17.1096 13.7721 -28.8886 l -20.0358 -56.5473 h 16.957 c 22.4616 0 40.7353 -18.2737 40.7353 -40.7353 V 473.461 c 0 -22.4616 -18.2737 -40.7353 -40.7374 -40.7353 Z" p-id="1865" /><path d="M 592.926 331.341 c -28.4857 0 -51.6618 23.174 -51.6618 51.6597 v 4.46348 h 320.895 v -4.46348 c 0 -28.4857 -23.174 -51.6597 -51.6597 -51.6597 H 592.926 Z M 213.503 331.341 c -28.4878 0 -51.6618 23.1762 -51.6618 51.6618 v 4.46136 h 320.895 v -4.46348 c 0 -28.4857 -23.174 -51.6597 -51.6618 -51.6597 H 213.503 Z" p-id="1866" /></svg>
                    <div style="padding: 14px;float: right">
                    <div>床位数统计</div>
                    <div style="color: yellow;text-align: center;font-size: 21px;font-weight: bold;">
                        <countTo :startVal='startVal' :endVal='endVal2' :duration='3000'></countTo>
                    </div>
                </div>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card :body-style="{ padding: '10px' }" shadow="hover" @click.native="drawLine('3')">
                    <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 1126 1024" width="64" height="64" t="1597563099592" p-id="2689" version="1.1"><path d="M 1119.22 428.406 l -21.3905 -24.5331 a 23.727 23.727 0 0 1 -5.84122 -15.5844 V 264.584 c 27.9677 -17.3601 72.0455 -31.6243 -28.3065 -74.9545 C 948.695 142.654 746.648 64.23 644.87 22.6756 c -0.490662 -0.198601 -8.91369 -3.90193 -9.34594 -4.08885 c -76.2045 -31.7996 -109.698 -18.2012 -171.358 6.49543 c -104.558 39.2062 -278.042 110.983 -397.378 155.902 c -91.5786 33.9608 -86.0177 59.3467 -1.44862 99.3824 c 126.602 60.001 369.492 152.175 423.196 172.152 s 92.4548 20.1055 146.591 -4.03044 c 51.6363 -23.0144 137.327 -52.1737 354.398 -141.077 a 23.727 23.727 0 0 1 32.7108 21.9513 v 57.4308 a 23.727 23.727 0 0 1 -6.06318 15.8414 l -23.7504 26.5892 a 23.727 23.727 0 0 0 1.34348 33.0613 l 43.0498 51.2391 a 23.727 23.727 0 0 0 31.858 0.712628 l 48.2017 -52.3022 a 23.727 23.727 0 0 0 2.34817 -33.5286 Z" p-id="2690" /><path d="M 941.02 494.891 c -93.9618 36.3674 -235.401 91.3215 -293.486 111.999 c -79.6041 28.3299 -101.509 18.0727 -166.229 -1.4603 c -89.4757 -27.0098 -220.319 -82.3027 -296.407 -108.728 a 23.213 23.213 0 0 0 -30.8066 21.9396 v 369.282 a 23.213 23.213 0 0 0 11.0983 19.8601 c 42.7577 26.1803 204.594 116.217 408.091 116.217 c 188.917 0 346.536 -89.9547 388.417 -116.193 a 23.213 23.213 0 0 0 10.9114 -19.6849 v -371.501 a 23.213 23.213 0 0 0 -31.5893 -21.7293 Z" p-id="2691" /></svg>
                    <div style="padding: 14px;float: right">
                        <div>按院系统计在校学生</div>
                        <div style="color: blue;text-align: center;font-size: 21px;font-weight: bold;">
                            <countTo :startVal='startVal' :endVal='endVal3' :duration='3000'></countTo>
                        </div>
                    </div>

                </el-card>
            </el-col>
        </el-row>

        <el-row :gutter="20" style="margin-bottom: 20px">
            <el-col :span="8 ">
                <el-card :body-style="{ padding: '10px' }" shadow="hover" @click.native="drawLine('4')">
                    <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 1126 1024" width="64" height="64" t="1597563099592" p-id="2689" version="1.1"><path d="M 1119.22 428.406 l -21.3905 -24.5331 a 23.727 23.727 0 0 1 -5.84122 -15.5844 V 264.584 c 27.9677 -17.3601 72.0455 -31.6243 -28.3065 -74.9545 C 948.695 142.654 746.648 64.23 644.87 22.6756 c -0.490662 -0.198601 -8.91369 -3.90193 -9.34594 -4.08885 c -76.2045 -31.7996 -109.698 -18.2012 -171.358 6.49543 c -104.558 39.2062 -278.042 110.983 -397.378 155.902 c -91.5786 33.9608 -86.0177 59.3467 -1.44862 99.3824 c 126.602 60.001 369.492 152.175 423.196 172.152 s 92.4548 20.1055 146.591 -4.03044 c 51.6363 -23.0144 137.327 -52.1737 354.398 -141.077 a 23.727 23.727 0 0 1 32.7108 21.9513 v 57.4308 a 23.727 23.727 0 0 1 -6.06318 15.8414 l -23.7504 26.5892 a 23.727 23.727 0 0 0 1.34348 33.0613 l 43.0498 51.2391 a 23.727 23.727 0 0 0 31.858 0.712628 l 48.2017 -52.3022 a 23.727 23.727 0 0 0 2.34817 -33.5286 Z" p-id="2690" /><path d="M 941.02 494.891 c -93.9618 36.3674 -235.401 91.3215 -293.486 111.999 c -79.6041 28.3299 -101.509 18.0727 -166.229 -1.4603 c -89.4757 -27.0098 -220.319 -82.3027 -296.407 -108.728 a 23.213 23.213 0 0 0 -30.8066 21.9396 v 369.282 a 23.213 23.213 0 0 0 11.0983 19.8601 c 42.7577 26.1803 204.594 116.217 408.091 116.217 c 188.917 0 346.536 -89.9547 388.417 -116.193 a 23.213 23.213 0 0 0 10.9114 -19.6849 v -371.501 a 23.213 23.213 0 0 0 -31.5893 -21.7293 Z" p-id="2691" /></svg>
                    <div style="padding: 14px;float: right">
                        <div>按专业统计在校学生</div>
                        <div style="color: greenyellow;text-align: center;font-size: 21px;font-weight: bold;">
                            <countTo :startVal='startVal' :endVal='endVal4' :duration='3000'></countTo>
                        </div>
                    </div>

                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card :body-style="{ padding: '10px' }" shadow="hover" @click.native="drawLine('5')">
                    <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 1126 1024" width="64" height="64" t="1597563099592" p-id="2689" version="1.1"><path d="M 1119.22 428.406 l -21.3905 -24.5331 a 23.727 23.727 0 0 1 -5.84122 -15.5844 V 264.584 c 27.9677 -17.3601 72.0455 -31.6243 -28.3065 -74.9545 C 948.695 142.654 746.648 64.23 644.87 22.6756 c -0.490662 -0.198601 -8.91369 -3.90193 -9.34594 -4.08885 c -76.2045 -31.7996 -109.698 -18.2012 -171.358 6.49543 c -104.558 39.2062 -278.042 110.983 -397.378 155.902 c -91.5786 33.9608 -86.0177 59.3467 -1.44862 99.3824 c 126.602 60.001 369.492 152.175 423.196 172.152 s 92.4548 20.1055 146.591 -4.03044 c 51.6363 -23.0144 137.327 -52.1737 354.398 -141.077 a 23.727 23.727 0 0 1 32.7108 21.9513 v 57.4308 a 23.727 23.727 0 0 1 -6.06318 15.8414 l -23.7504 26.5892 a 23.727 23.727 0 0 0 1.34348 33.0613 l 43.0498 51.2391 a 23.727 23.727 0 0 0 31.858 0.712628 l 48.2017 -52.3022 a 23.727 23.727 0 0 0 2.34817 -33.5286 Z" p-id="2690" /><path d="M 941.02 494.891 c -93.9618 36.3674 -235.401 91.3215 -293.486 111.999 c -79.6041 28.3299 -101.509 18.0727 -166.229 -1.4603 c -89.4757 -27.0098 -220.319 -82.3027 -296.407 -108.728 a 23.213 23.213 0 0 0 -30.8066 21.9396 v 369.282 a 23.213 23.213 0 0 0 11.0983 19.8601 c 42.7577 26.1803 204.594 116.217 408.091 116.217 c 188.917 0 346.536 -89.9547 388.417 -116.193 a 23.213 23.213 0 0 0 10.9114 -19.6849 v -371.501 a 23.213 23.213 0 0 0 -31.5893 -21.7293 Z" p-id="2691" /></svg>
                    <div style="padding: 14px;float: right">
                        <div>按年级统计在校学生</div>
                        <div style="color: hotpink;text-align: center;font-size: 21px;font-weight: bold;">
                            <countTo :startVal='startVal' :endVal='endVal5' :duration='3000'></countTo>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card :body-style="{ padding: '10px' }" shadow="hover" @click.native="drawLine('6')">
                    <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 1126 1024" width="64" height="64" t="1597563099592" p-id="2689" version="1.1"><path d="M 1119.22 428.406 l -21.3905 -24.5331 a 23.727 23.727 0 0 1 -5.84122 -15.5844 V 264.584 c 27.9677 -17.3601 72.0455 -31.6243 -28.3065 -74.9545 C 948.695 142.654 746.648 64.23 644.87 22.6756 c -0.490662 -0.198601 -8.91369 -3.90193 -9.34594 -4.08885 c -76.2045 -31.7996 -109.698 -18.2012 -171.358 6.49543 c -104.558 39.2062 -278.042 110.983 -397.378 155.902 c -91.5786 33.9608 -86.0177 59.3467 -1.44862 99.3824 c 126.602 60.001 369.492 152.175 423.196 172.152 s 92.4548 20.1055 146.591 -4.03044 c 51.6363 -23.0144 137.327 -52.1737 354.398 -141.077 a 23.727 23.727 0 0 1 32.7108 21.9513 v 57.4308 a 23.727 23.727 0 0 1 -6.06318 15.8414 l -23.7504 26.5892 a 23.727 23.727 0 0 0 1.34348 33.0613 l 43.0498 51.2391 a 23.727 23.727 0 0 0 31.858 0.712628 l 48.2017 -52.3022 a 23.727 23.727 0 0 0 2.34817 -33.5286 Z" p-id="2690" /><path d="M 941.02 494.891 c -93.9618 36.3674 -235.401 91.3215 -293.486 111.999 c -79.6041 28.3299 -101.509 18.0727 -166.229 -1.4603 c -89.4757 -27.0098 -220.319 -82.3027 -296.407 -108.728 a 23.213 23.213 0 0 0 -30.8066 21.9396 v 369.282 a 23.213 23.213 0 0 0 11.0983 19.8601 c 42.7577 26.1803 204.594 116.217 408.091 116.217 c 188.917 0 346.536 -89.9547 388.417 -116.193 a 23.213 23.213 0 0 0 10.9114 -19.6849 v -371.501 a 23.213 23.213 0 0 0 -31.5893 -21.7293 Z" p-id="2691" /></svg>
                    <div style="padding: 14px;float: right">
                        <div>按宿舍楼统计在校学生</div>
                        <div style="color: purple;text-align: center;font-size: 21px;font-weight: bold;">
                            <countTo :startVal='startVal' :endVal='endVal6' :duration='3000'></countTo>
                        </div>
                    </div>

                </el-card>
            </el-col>
        </el-row>

        <el-row :gutter="20" style="margin-bottom: 20px">

            <el-col :span="8">
                <el-card :body-style="{ padding: '10px' }" shadow="hover" @click.native="drawLine('7')">
                    <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 1024 1024" width="64" height="64" t="1597563257665" p-id="4619" version="1.1"><path fill="#666666" d="M 827.8 959.7 H 196.2 V 64.3 h 631.6 v 895.4 Z m -589.7 -41.9 h 547.7 V 106.2 H 238.1 v 811.6 Z" p-id="4620" /><path fill="#666666" d="M 147.1 916.5 h 729.8 v 41.9 H 147.1 Z M 639.1 437.7 c 31.3 0 56.6 25.3 56.6 56.6 s -25.3 56.6 -56.6 56.6 s -56.6 -25.3 -56.6 -56.6 s 25.3 -56.6 56.6 -56.6 m 0 -42 c -54.3 0 -98.5 44.2 -98.5 98.5 s 44.2 98.5 98.5 98.5 s 98.5 -44.2 98.5 -98.5 s -44.2 -98.5 -98.5 -98.5 Z" p-id="4621" /><path fill="#666666" d="M 618.1 571.9 H 660 v 81.8 h -41.9 Z" p-id="4622" /></svg>
                    <div style="padding: 14px;float: right">
                        <div>院系分配房间统计</div>
                        <div style="color: aqua;text-align: center;font-size: 21px;font-weight: bold;">
                            <countTo :startVal='startVal' :endVal='endVal7' :duration='3000'></countTo>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card :body-style="{ padding: '10px' }" shadow="hover" @click.native="drawLine('8')">
                    <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 1024 1024" width="64" height="64" t="1597580605546" p-id="5181" version="1.1"><path d="M 435.45 128.545 l -75.7247 0 l 0 151.449 l 75.7247 0 L 435.45 128.545 Z M 779.958 205.293 l -77.4244 0 l 0 113.587 l -152.473 0 l 0 -113.587 l -76.748 0 l 0 113.587 l -154.519 0 l 0 -113.587 l -74.7761 0 c -21.1384 0 -39.8342 16.027 -39.8342 37.1675 l 0 612.496 c 0 21.1425 18.6958 37.9984 39.8342 37.9984 L 779.958 892.954 c 21.1333 0 37.1859 -16.8549 37.1859 -37.9984 L 817.144 242.461 C 817.143 221.321 801.091 205.293 779.958 205.293 Z M 665.693 128.545 l -77.7713 0 l 0 151.449 l 77.7713 0 L 665.693 128.545 Z" p-id="5182" /></svg>
                    <div style="padding: 14px;float: right">
                    <div>学院违规操行统计</div>
                    <div style="color: orangered;text-align: center;font-size: 21px;font-weight: bold;">
                        <countTo :startVal='startVal' :endVal='endVal8' :duration='3000'></countTo>
                    </div>
                </div>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card :body-style="{ padding: '10px' }" shadow="hover" @click.native="drawLine('9')" >
                    <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 1024 1024" width="64" height="64" t="1597580647706" p-id="5488" version="1.1"><path d="M 98 928.3 V 790 h 69.2 v 69.2 h 691.7 V 790 h 69.2 v 138.3 H 98 Z m 760.9 -207.5 H 720.5 V 374.9 h 138.4 v 345.9 Z m -415 0 V 444.1 h 138.3 v 276.7 H 443.9 Z m -276.7 0 v -415 h 138.4 v 415 H 167.2 Z m 691.7 -553.4 H 167.2 v 69.2 H 98 V 98.2 h 830.1 v 138.4 h -69.2 v -69.2 Z" p-id="5489" /></svg>
                    <div style="padding: 14px;float: right">
                    <div>近六月违规操行统计</div>
                    <div style="color: green;text-align: center;font-size: 21px;font-weight: bold;">
                        <countTo :startVal='startVal' :endVal='endVal9' :duration='3000'></countTo>
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
                endVal5: 0,
                endVal6: 0,
                endVal7: 0,
                endVal8: 0,
                endVal9: 0,
                e1data: {},
                e2data: {},
                e3data: [],
                e4data: [],
                e5data: [],
                e6data: [],
                e7data: [],
                e8data: [],
                e9data: [],
                e9data2: [],
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
                        url: "api/statistics/getRoomCount"
                    }).then((result) => {
                        this.e1data = result.data;
                        //console.log(this.e1data.p1);
                        let p1 = this.e1data.p1;
                        let p2 = this.e1data.p2;
                        let p3 = this.e1data.p3;
                        arr.push({value: p1, name: '空宿舍数量'});
                        arr.push({value: p2, name: '满员宿舍数量'});
                        arr.push({value: p3, name: '未满员宿舍数量'});
                        myChart.setOption({
                            title: {
                                text: '房间数统计',
                                left: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: '{a} <br/>{b} : {c} ({d}%)'
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left',
                                data: ['空宿舍数量','满员宿舍数量','未满员宿舍数量']
                            },
                            series: [
                                {
                                    name: '房间数统计',
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
                        url: "api/statistics/getBedCount"
                    }).then((result) => {
                        this.e2data = result.data;
                        //console.log(this.e1data.p1);
                        let p1 = this.e2data.p1;
                        let p2 = this.e2data.p2;

                        arr.push({value: p1, name: '已占满的床位数'});
                        arr.push({value: p2, name: '未占满的床位数'});

                        myChart.setOption({
                            title: {
                                text: '床位数统计',
                                left: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: '{a} <br/>{b} : {c} ({d}%)'
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left',
                                data: ['已占满的床位数','未占满的床位数']
                            },
                            series: [
                                {
                                    name: '床位数统计',
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
                    let p1 = [];
                    let p2 = [];
                    let p = {};
                    this.$axios({
                        method: "get",
                        url: "api/statistics/getColCount"
                    }).then((result) => {
                        this.e3data = result.data;
                        //console.log(this.e3data);
                        p1 = this.e3data.p1;
                        p2 = this.e3data.p2;
                        //console.log(p1.length);
                        for (let i = 0; i <p2.length ; i++) {
                            p = {value: p2[i],name: p1[i]};
                            //console.log(p);
                            arr.push(p);
                        }
                        //console.log(arr);
                        myChart.setOption({
                            title: {
                                text: '按院系统计在校学生',
                                left: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: '{a} <br/>{b} : {c} ({d}%)'
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left',
                                data: p1
                            },
                            series: [
                                {
                                    name: '按院系统计在校学生',
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
                                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                                        }
                                    }
                                }
                            ]
                        },true);
                    });

                }
                else if(index==4) {
                    let arr = [];
                    let p1 = [];
                    let p2 = [];
                    let p = {};
                    this.$axios({
                        method: "get",
                        url: "api/statistics/getMajorCount"
                    }).then((result) => {
                        this.e4data = result.data;
                        //console.log(this.e3data);
                        p1 = this.e4data.p1;
                        p2 = this.e4data.p2;
                        //console.log(p1.length);
                        for (let i = 0; i <p2.length ; i++) {
                            p = {value: p2[i],name: p1[i]};
                            //console.log(p);
                            arr.push(p);
                        }
                        //console.log(arr);
                        myChart.setOption({
                            title: {
                                text: '按专业统计在校学生',
                                left: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: '{a} <br/>{b} : {c} ({d}%)'
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left',
                                data: p1
                            },
                            series: [
                                {
                                    name: '按专业统计在校学生',
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
                                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                                        }
                                    }
                                }
                            ]
                        },true);
                    });

                }
                else if(index==5) {
                    let arr = [];
                    let p1 = [];
                    let p2 = [];
                    let p = {};
                    this.$axios({
                        method: "get",
                        url: "api/statistics/getGradeCount"
                    }).then((result) => {
                        this.e5data = result.data;
                        //console.log(this.e3data);
                        p1 = this.e5data.p1;
                        p2 = this.e5data.p2;
                        //console.log(p1.length);
                        for (let i = 0; i <p2.length ; i++) {
                            p = {value: p2[i],name: p1[i]};
                            //console.log(p);
                            arr.push(p);
                        }
                       // console.log(arr);
                        myChart.setOption({
                            title: {
                                text: '按年级统计在校学生',
                                left: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: '{a} <br/>{b} : {c} ({d}%)'
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left',
                                data: p1
                            },
                            series: [
                                {
                                    name: '按年级统计在校学生',
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
                                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                                        }
                                    }
                                }
                            ]
                        },true);
                    });

                }
                else if(index==6) {
                    let arr = [];
                    let p1 = [];
                    let p2 = [];
                    let p = {};
                    this.$axios({
                        method: "get",
                        url: "api/statistics/getBldCount"
                    }).then((result) => {
                        this.e6data = result.data;
                        //console.log(this.e3data);
                        p1 = this.e6data.p1;
                        p2 = this.e6data.p2;
                        //console.log(p1.length);
                        for (let i = 0; i <p2.length ; i++) {
                            p = {value: p2[i],name: p1[i]};
                            //console.log(p);
                            arr.push(p);
                        }
                        //console.log(arr);
                        myChart.setOption({
                            title: {
                                text: '按宿舍楼统计在校学生',
                                left: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: '{a} <br/>{b} : {c} ({d}%)'
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left',
                                data: p1
                            },
                            series: [
                                {
                                    name: '按宿舍楼统计在校学生',
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
                                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                                        }
                                    }
                                }
                            ]
                        },true);
                    });

                }
                else if(index==7){
                    let arr = [];
                    let p1 = [];
                    let p2 = [];
                    let p = {};
                    this.$axios({
                        method: "get",
                        url: "api/statistics/getRoomByCol"
                    }).then((result) => {
                        this.e7data = result.data;
                        //console.log(this.e3data);
                        p1 = this.e7data.p1;
                        p2 = this.e7data.p2;
                        //console.log(p1.length);
                        for (let i = 0; i <p2.length ; i++) {
                            p = {value: p2[i],name: p1[i]};
                            //console.log(p);
                            arr.push(p);
                        }
                        //console.log(arr);
                        myChart.setOption({
                            title: {
                                text: '按宿舍楼统计在校学生',
                                left: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: '{a} <br/>{b} : {c} ({d}%)'
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left',
                                data: p1
                            },
                            series: [
                                {
                                    name: '按宿舍楼统计在校学生',
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
                                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                                        }
                                    }
                                }
                            ]
                        },true);
                    });

                }
                else if(index==8) {
                    let arr = [];
                    let p1 = [];
                    let p2 = [];
                    let p = {};
                    this.$axios({
                        method: "get",
                        url: "api/statistics/getActionByCol"
                    }).then((result) => {
                        this.e8data = result.data;
                        //console.log(this.e3data);
                        p1 = this.e8data.p1;
                        p2 = this.e8data.p2;
                        //console.log(p1.length);
                        for (let i = 0; i <p2.length ; i++) {
                            p = {value: p2[i],name: p1[i]};
                            //console.log(p);
                            arr.push(p);
                        }
                        //console.log(arr);
                        myChart.setOption({
                            title: {
                                text: '学院违规操行',
                                left: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: '{a} <br/>{b} : {c} ({d}%)'
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left',
                                data: p1
                            },
                            series: [
                                {
                                    name: '学院违规操行',
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
                                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                                        }
                                    }
                                }
                            ]
                        },true);
                    });

                }
                else if(index==9){
                    this.$axios({
                        method: "get",
                        url: "api/statistics/getActionCount"
                    }).then((result) => {
                        this.e9data = result.data;
                        let p1 = result.data.p1;
                        let p2 = result.data.p2;
                        myChart.setOption({
                            title: {
                                text: '近六月违规操行统计',
                                left: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: '{a} <br/>{b} : {c} '
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left',              //设置区分（哪条线属于什么）
                                data: ['近六月违规操行']

                            },
                            color: [ '#80fa3f'],       //设置区分（每条线是什么颜色，和 legend 一一对应）
                            xAxis: {                //设置x轴
                                type: 'category',
                                boundaryGap: false,     //坐标轴两边不留白
                                data: p1,
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
                                name: '违规操行数量',
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
                                    name: '近六月违规操行',
                                    data: p2,
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
                    url: "api/statistics/getRoomCount"
                }).then((result) => {
                    //this.e1data = result.data;
                    //console.log(this.e1data.p1);
                    let p1 = result.data.p1;
                    let p2 = result.data.p2;
                    let p3 = result.data.p3;
                    this.endVal1 = p1+p2+p3;
                });

                this.$axios({
                    method: "get",
                    url: "api/statistics/getBedCount"
                }).then((result) => {
                    this.e2data = result.data;
                    //console.log(this.e1data.p1);
                    let p1 = result.data.p1;
                    let p2 = result.data.p2;
                    this.endVal2 = p1+p2;

                });

                this.$axios({
                    method: "get",
                    url: "api/statistics/getColCount"
                }).then((result) => {
                    //this.e3data = result.data;
                    //console.log(this.e1data.p1);
                    let p1 = result.data.p1;
                    let p2 = result.data.p2;
                    let a = 0;
                    p2.forEach(item => {
                        a = a + item;
                    });
                    this.endVal3 = a;
                });

                this.$axios({
                    method: "get",
                    url: "api/statistics/getMajorCount"
                }).then((result) => {
                    this.e3data = result.data;
                    //console.log(this.e1data.p1);
                    let p1 = result.data.p1;
                    let p2 = result.data.p2;
                    let a = 0;
                    p2.forEach(item => {
                        a = a + item;
                    });
                    this.endVal4 = a;
                });

                this.$axios({
                    method: "get",
                    url: "api/statistics/getGradeCount"
                }).then((result) => {
                    //this.e3data = result.data;
                    //console.log(this.e1data.p1);
                    let p1 = result.data.p1;
                    let p2 = result.data.p2;
                    let a = 0;
                    p2.forEach(item => {
                        a = a + item;
                    });
                    this.endVal5 = a;
                });

                this.$axios({
                    method: "get",
                    url: "api/statistics/getBldCount"
                }).then((result) => {
                    //this.e3data = result.data;
                    //console.log(this.e1data.p1);
                    let p1 = result.data.p1;
                    let p2 = result.data.p2;
                    let a = 0;
                    p2.forEach(item => {
                        a = a + item;
                    });
                    this.endVal6 = a;
                });

                this.$axios({
                    method: "get",
                    url: "api/statistics/getRoomByCol"
                }).then((result) => {
                    this.e3data = result.data;
                    //console.log(this.e1data.p1);
                    let p1 = result.data.p1;
                    let p2 = result.data.p2;
                    let a = 0;
                    p2.forEach(item => {
                        a = a + item;
                    });
                    this.endVal7 = a;
                });

                this.$axios({
                    method: "get",
                    url: "api/statistics/getActionByCol"
                }).then((result) => {
                    //this.e3data = result.data;
                    //console.log(this.e1data.p1);
                    let p1 = result.data.p1;
                    let p2 = result.data.p2;
                    let a = 0;
                    p2.forEach(item => {
                        a = a + item;
                    });
                    this.endVal8 = a;
                });

                this.$axios({
                    method: "get",
                    url: "api/statistics/getActionCount"
                }).then((result) => {
                    //this.e4data = result.data;
                    //console.log(this.e1data.p1);
                    let p1 = result.data.p1;
                    let p2 = result.data.p2;
                    let a = 0;
                    p2.forEach(item => {
                        a = a + item;
                    });
                    this.endVal9 = a;
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