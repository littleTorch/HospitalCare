<template>
  <el-container>
    <el-header style="padding:20px 10px 10px 10px;text-align: center">
      <el-form size="mini" :inline="true" :model="doStationForm" class="demo-form-inline">
        <el-row>
          <el-col span="">
            <el-form-item label="护理项目" prop="station">
              <el-select v-model="doStationForm.stationId" placeholder="请选择">
                <el-option
                        v-for="item in stations"
                        :key="item.stationId"
                        :label="item.stationName"
                        :value="item.stationId">
                </el-option>
              </el-select>
            </el-form-item>
            <el-button @click="selectByLike" size="mini" type="primary" round>查询</el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-header>
    <el-main>
      <!--数据表格-->
      <el-table
              :data="doStationData.slice((page.currentPage-1) * page.pageSize ,page.currentPage * page.pageSize)"
              :height="tableHeight"
              size="mini"
              border
              style="width: 100%"
      >
        <el-table-column align="center" prop="stationId" label="外键站点id"></el-table-column>
        <el-table-column align="center" prop="mainContent" label="维护内容"></el-table-column>
        <el-table-column align="center" prop="mainTime" label="维护时间"></el-table-column>
        <el-table-column align="center" prop="mainPerson" label="维护人" sortable></el-table-column>
        <el-table-column align="center" prop="note" label="备注" show-overflow-tooltip></el-table-column>

        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button @click="ops(scope.row)" type="success" size="mini">运维</el-button>
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

      <el-dialog title="站点运维结果" :visible.sync="updateOneDoStationVisible" width="40%">
        <el-form
                size="mini"
                ref="updateData"
                style="text-align:center"
                :model="updateData"
                :inline="true"
                label-width="100px"
                status-icon
                :rules="rules"
        >
          <el-form-item label="站点id">
            <el-input v-model="updateData.stationId" placeholder="外键站点id" disabled></el-input>
          </el-form-item>
          <el-form-item label="维护内容" prop="mainContent">
            <el-input v-model="updateData.mainContent" placeholder="维护内容"></el-input>
          </el-form-item>
          <el-form-item label="维护时间" prop="mainTime" label-width="5">
            <el-date-picker
                    v-model="updateData.mainTime"
                    type="datetime"
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="维护人" prop="mainPerson">
            <el-input v-model="updateData.mainPerson" placeholder="维护人"></el-input>
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="updateData.note" placeholder="备注"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
        <el-button @click="updateOneDoStationVisible = false">取 消</el-button>
        <el-button @click="updateSubmit()" type="primary">确 定</el-button>
      </span>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>

  import qs from "qs";

  export default {
    name: "ServiceFocus",
    methods: {

      getStationList(){
        this.$axios('api/station').then(res => {
          this.stations = res.data;
        })
      },

      selectByLike() {
        if (this.doStationForm.stationId==null||this.doStationForm.stationId=="")return;
        this.isSelectByLike = true;
        this.$axios('api/stationMaintain/selectByLike', {
          params: {
            stationId: this.doStationForm.stationId
          }
        }).then(res => {
          this.doStationData = res.data;
          this.page.totalCount = res.data.length;
        });
      },

      ops(row){
        this.updateOneDoStationVisible = true;
        this.updateData = row;
      },

      updateSubmit(){
        this.$axios.post("api/stationMaintain/saveOnDoStation",  qs.stringify(this.updateData)).then(res => {
          if (res.data.code == 200){
            this.$message({
              type: "success",
              message: res.data.msg
            })
            this.updateOneDoStationVisible = false;
            if (this.isSelectByLike){
              this.selectByLike();
            } else {
              this.getTableData();
            }
          } else {
            this.$message({
              type: "success",
              message: res.data.msg
            })
          }
        })
      },

      //pageSize改变调用
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },

      getTableData() {
        this.isSelectByLike=false;
        this.$axios('api/stationMaintain/doStation').then(res => {
          this.doStationData = res.data;
          this.page.totalCount = res.data.length;
        });
      },

      // 页数改变调用
      handleCurrentChange(val) {
        // console.log(`当前页: ${val}`);
        this.page.currentPage = val;
      },
    },
    data(){
      return {
        //表单验证
        rules: {
          mainContent: [
            { required: true, message: "维护内容不能为空", trigger: "blur" },
          ],
          mainTime: [
            { required: true, message: "维护时间不能为空", trigger: "blur" },
          ],
          mainPerson: [
            { required: true, message: "维护人不能为空", trigger: "blur" },
          ],
        },

        // 表格高度
        tableHeight: window.innerHeight,
        isSelectByLike: false,
        page: {
          currentPage: 1, // 当前页
          pageSize: 10, // 每页显示条目个数
          totalCount: 0 // 总条目数
        },
        doStationForm:{
          stationId: "",
        },
        doStationData: [],
        stations: [],
        updateData: [],
        updateOneDoStationVisible: false,
      };
    },
    created: function () {
      this.getStationList();
      this.getTableData();
    },
    mounted() {
      this.$nextTick(() => {
        this.tableHeight = window.innerHeight - 280;
      });
    },
  }
</script>

<style scoped>

</style>