<template>
  <el-container>
    <el-header style="padding:20px 10px 10px 10px;text-align: center">
      <el-form size="mini" :inline="true" :model="serviceObjForm" class="demo-form-inline">
        <el-row>
          <el-col span="">
            <el-form-item label="客户名" prop="serviceObj">
              <el-input v-model="serviceObjForm.cusName" placeholder="请输入要查询的客户名"></el-input>
            </el-form-item>
            <el-button @click="selectByLike" size="mini" type="primary" round>查询</el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-header>
    <el-main>
      <!--数据表格-->
      <el-table
              :data="serviceObjData"
              :height="tableHeight"
              size="mini"
              border
              style="width: 100%"
      >
        <el-table-column align="center" prop="cusName" label="客户名"></el-table-column>
        <el-table-column align="center" prop="cusSex" label="性别"></el-table-column>
        <el-table-column align="center" prop="cusAge" label="年龄"></el-table-column>
        <el-table-column align="center" prop="cusIdcard" label="身份证号" sortable></el-table-column>
        <el-table-column align="center" prop="elderType" label="老人类型" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="expirationDate" label="合同到期日期" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="phone" label="联系电话" show-overflow-tooltip></el-table-column>

        <el-table-column label="操作" width="300" align="center">
          <template slot-scope="scope">
            <el-button @click="details(scope.row.id)" type="primary" size="mini">详情</el-button>
            <el-button @click="updateServiceObj(scope.row.id)" type="success" size="mini">设置</el-button>
            <el-popconfirm
                    style="margin-left: 10px;"
                    title="您确定删除吗？"
                    icon="el-icon-info"
                    iconColor="red"
                    @onConfirm="deleteCustomer(scope.row.id)"
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

      <el-dialog title="服务对象详情" :visible.sync="serviceObjDetailsVisible" width="40%">
        <el-form
                :model="serviceObjDetails"
                size="mini"
                style="text-align:center"
                :inline="true"
                label-width="100px"
                disabled
        >
          <el-form-item label="客户id">
            <el-input v-model="serviceObjDetails.id"></el-input>
          </el-form-item>
          <el-form-item label="创建者">
            <el-input v-model="serviceObjDetails.createBy"></el-input>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker
                    v-model="serviceObjDetails.createTime"
                    type="datetime"
                    placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="更新者">
            <el-input v-model="serviceObjDetails.updateBy"></el-input>
          </el-form-item>
          <el-form-item label="更新时间">
            <el-date-picker
                    v-model="serviceObjDetails.createTime"
                    type="datetime"
                    placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="客户名">
            <el-input v-model="serviceObjDetails.cusName"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="serviceObjDetails.cusSex">
              <el-radio :label="0">男</el-radio>
              <el-radio :label="1">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="serviceObjDetails.cusAge"></el-input>
          </el-form-item>
          <el-form-item label="老人类型">
            <el-select v-model="serviceObjDetails.elderType" placeholder="请选择">
              <el-option
                      v-for="item in elderTypeS"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="合同到期日期">
            <el-date-picker
                    v-model="serviceObjDetails.expirationDate"
                    type="datetime"
                    placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="serviceObjDetails.phone"></el-input>
          </el-form-item>
          <el-form-item label="身心状况">
            <el-input v-model="serviceObjDetails.psychologicalStatus"></el-input>
          </el-form-item>
          <el-form-item label="注意">
            <el-input v-model="serviceObjDetails.attentin"></el-input>
          </el-form-item>
          <el-form-item label="出生年月">
            <el-date-picker
                    v-model="serviceObjDetails.birthday"
                    type="datetime"
                    placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="身高">
            <el-input v-model="serviceObjDetails.height"></el-input>
          </el-form-item>
          <el-form-item label="体重">
            <el-input v-model="serviceObjDetails.weight"></el-input>
          </el-form-item>
          <el-form-item label="婚姻状态">
            <el-radio-group v-model="serviceObjDetails.maritalStatus">
              <el-radio :label="0">已婚</el-radio>
              <el-radio :label="1">丧偶/丧夫</el-radio>
              <el-radio :label="2">未婚</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="血型">
            <el-input v-model="serviceObjDetails.bloodType"></el-input>
          </el-form-item>
          <el-form-item label="头像存储">
            <el-input v-model="serviceObjDetails.headImg"></el-input>
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
        <el-button @click="serviceObjDetailsVisible = false">取 消</el-button>
      </span>
      </el-dialog>

      <el-dialog title="设置服务对象" :visible.sync="serviceObjUpdateVisible" width="40%">
        <el-form
                :model="serviceObjUpdateData"
                size="mini"
                style="text-align:center"
                :inline="true"
                label-width="100px"
        >
          <el-form-item label="客户id">
            <el-input v-model="serviceObjUpdateData.id" disabled></el-input>
          </el-form-item>
          <el-form-item label="创建者">
            <el-input v-model="serviceObjUpdateData.createBy"></el-input>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker
                    v-model="serviceObjUpdateData.createTime"
                    type="datetime"
                    placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="更新者">
            <el-input v-model="serviceObjUpdateData.updateBy"></el-input>
          </el-form-item>
          <el-form-item label="更新时间">
            <el-date-picker
                    v-model="serviceObjUpdateData.createTime"
                    type="datetime"
                    placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="客户名">
            <el-input v-model="serviceObjUpdateData.cusName"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="serviceObjUpdateData.cusSex">
              <el-radio :label="0">男</el-radio>
              <el-radio :label="1">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="serviceObjUpdateData.cusAge"></el-input>
          </el-form-item>
          <el-form-item label="老人类型">
            <el-select v-model="serviceObjUpdateData.elderType" placeholder="请选择">
              <el-option
                      v-for="item in elderTypeS"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="合同到期日期">
            <el-date-picker
                    v-model="serviceObjUpdateData.expirationDate"
                    type="datetime"
                    placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="serviceObjUpdateData.phone"></el-input>
          </el-form-item>
          <el-form-item label="身心状况">
            <el-input v-model="serviceObjUpdateData.psychologicalStatus"></el-input>
          </el-form-item>
          <el-form-item label="注意">
            <el-input v-model="serviceObjUpdateData.attentin"></el-input>
          </el-form-item>
          <el-form-item label="出生年月">
            <el-date-picker
                    v-model="serviceObjUpdateData.birthday"
                    type="datetime"
                    placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="身高">
            <el-input v-model="serviceObjUpdateData.height"></el-input>
          </el-form-item>
          <el-form-item label="体重">
            <el-input v-model="serviceObjUpdateData.weight"></el-input>
          </el-form-item>
          <el-form-item label="婚姻状态">
            <el-radio-group v-model="serviceObjUpdateData.maritalStatus">
              <el-radio :label="0">已婚</el-radio>
              <el-radio :label="1">丧偶/丧夫</el-radio>
              <el-radio :label="2">未婚</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="血型">
            <el-input v-model="serviceObjUpdateData.bloodType"></el-input>
          </el-form-item>
          <el-form-item label="头像存储">
            <el-input v-model="serviceObjUpdateData.headImg"></el-input>
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button @click="serviceObjUpdateVisible = false">取 消</el-button>
          <el-button @click="updateSubmit()" type="primary">确 定</el-button>
        </span>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>

  import qs from "qs";

  export default {
    name: "ServiceObj",
    methods: {
      selectByLike() {
        if (this.serviceObjForm.cusName==null||this.serviceObjForm.cusName=="")return;
        this.isSelectByLike = true;
        this.$axios('api/serviceObj/selectByLike', {
          params: {
            cusName: this.serviceObjForm.cusName
          }
        }).then(res => {
          this.serviceObjData = res.data.data;
          this.page.totalCount = res.data.data.length;
        });
      },

      details(id){
        this.serviceObjDetailsVisible = true;
        this.$axios("api/serviceObj/getCustomer", {
          params: {
            id: id
          }
        }).then(res => {
          this.serviceObjDetails = res.data;
        })
      },

      updateServiceObj(id) {
        this.serviceObjUpdateVisible = true;
        this.$axios("api/serviceObj/getCustomer", {
          params: {
            id: id
          }
        }).then(res => {
          this.serviceObjUpdateData = res.data;
        })
      },

      updateSubmit(){
        this.$axios.post("api/serviceObj/updateCustomer",  qs.stringify(this.serviceObjUpdateData)).then(res => {
          if (res.data.code == 200){
            this.$message({
              type: "success",
              message: res.data.msg
            })
            this.serviceObjUpdateVisible = false;
            if (this.isSelectByLike){
              this.selectByLike();
            } else {
              this.getTableData(this.page.currentPage);
            }
          } else {
            this.$message({
              type: "error",
              message: res.data.msg
            })
          }
        })
      },

      deleteCustomer(id) {
        this.$axios({
          method: 'get',
          url: 'api/serviceObj/deleteCustomerById',
          params: {
            id: id
          }
        }).then((result) => {
          if (result.data.code == 200) {
            this.$message({
              type: "success",
              message: result.data.msg,
            });
            if (((this.page.totalCount-1)%this.page.pageSize==0)&&(this.page.currentPage!=1)){
              this.getTableData(this.page.currentPage-1);
            } else {
              this.getTableData(this.page.currentPage);
            }
          } else {
            this.$message({
              type: "error",
              message: result.data.msg,
            });
          }
        });
      },

      //pageSize改变调用
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },

      getTableData(pageNo) {
        let self = this
        self.$axios('api/serviceObj/getCustomerList', {
          params: {
            pageSize: 10,
            currentPage: pageNo
          }
        }).then(res => {
          if (res.data.code == 200) {
            self.serviceObjData = res.data.data.records;
            self.$set(self.page, 'totalCount', res.data.data.total)
            self.$set(self.page, 'pageSize', res.data.data.size)
            self.$set(self.page, 'currentPage', res.data.data.current)
          }
        });
      },
      // 页数改变调用
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.getTableData(val)
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
        serviceObjForm:{
          cusName: "",
        },
        serviceObjData: [],
        serviceObjs: [],
        serviceObjDetails: [],
        serviceObjUpdateData: [],
        serviceObjDetailsVisible: false,
        serviceObjUpdateVisible: false,
        elderTypeS:[
          {
            value: 0,
            label: "普通老人"
          },
          {
            value: 1,
            label: "残疾老人"
          }
        ]
      };
    },
    created: function () {
      this.getTableData(1);
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