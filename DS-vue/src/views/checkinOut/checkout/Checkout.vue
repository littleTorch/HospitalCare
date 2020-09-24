<template>
  <el-container>
    <el-header style="padding:20px 10px 10px 10px;text-align: center">
      <el-form size="mini" :inline="true" :model="checkoutForm" class="demo-form-inline">
        <el-row>
          <el-col span="">
            <el-form-item label="客户名" prop="serviceFocus">
              <el-input v-model="checkoutForm.cusName" placeholder="请输入要查询的客户名"></el-input>
            </el-form-item>
            <el-button @click="selectByLike" size="mini" type="primary" round>查询</el-button>
            <el-button @click="addOpen" size="mini" type="warning" round>退住登记</el-button>
            <el-button @click="delBatch" size="mini" type="danger" round>批量删除</el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-header>
    <el-main>
      <!--数据表格-->
      <el-table
              :data="checkoutData"
              size="mini"
              :height="tableHeight"
              @selection-change="handleSelectionChange"
              border
              style="width: 100%"
              tooltip-effect="dark"
      >
        <el-table-column align="center" type="selection"></el-table-column>
        <el-table-column align="center" prop="customer.cusName" label="退住人姓名"></el-table-column>
        <el-table-column align="center" prop="bed.floor" label="宿舍楼层"></el-table-column>
        <el-table-column align="center" prop="bed.roomNo" label="宿舍号"></el-table-column>
        <el-table-column align="center" prop="bed.bedNo" label="床位号"></el-table-column>
        <el-table-column align="center" prop="checkoutDate" label="退住申请日期" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="status" :formatter="statusF" label="退住状态" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="audit" :formatter="auditF" label="审核情况" ></el-table-column>
        <el-table-column align="center" prop="checkoutReason" label="退住原因" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" width="350" align="center">
          <template slot-scope="scope">
            <el-button @click="details(scope.row)" type="primary" size="mini">详情</el-button>
            <el-button @click="checkoutEdit(scope.row)" type="info" size="mini" :disabled="isEnableCheckoutEdit(scope.row)">编辑</el-button>
            <el-button @click="checkoutAudit(scope.row)" type="warning" size="mini" :disabled="scope.row.audit!=0">审核</el-button>
            <el-popconfirm
                    style="margin-left: 10px;"
                    title="您确定退住吗？"
                    icon="el-icon-info"
                    iconColor="red"
                    @onConfirm="checkoutDo(scope.row)"
            >
              <el-button size="mini" type="success" slot="reference" :disabled="isEnableCheckout(scope.row)">退住</el-button>
            </el-popconfirm>
            <el-popconfirm
                    style="margin-left: 10px;"
                    title="您确定删除吗？"
                    icon="el-icon-info"
                    iconColor="red"
                    @onConfirm="delCheckout(scope.row.id)"
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

      <el-dialog title="新增退住登记" :visible.sync="checkoutAddVisible" width="30%">
        <el-form
                :rules="rulesAdd"
                ref="checkoutAddData"
                size="mini"
                style="text-align:left"
                :model="checkoutAddData"
                :inline="true"
                label-width="100px"
        >
          <el-row>
            <el-form-item label="退住人信息：" prop="cusId" label-width="100">
              <el-select v-model="checkoutAddData.cusId" clearable placeholder="请选择">
                <el-option
                        v-for="item in checkoutAddVoList"
                        :key="item.cusId"
                        :label="item.label"
                        :value="item.cusId"
                        :disabled="item.disabled">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>

          <el-row>
            <el-form-item label="退住申请日期：" prop="checkoutDate" label-width="100">
              <el-date-picker
                      v-model="checkoutAddData.checkoutDate"
                      type="datetime"
                      placeholder="选择退住日期时间">
              </el-date-picker>
            </el-form-item>
          </el-row>

          <el-row>
            <el-form-item label="退住类型：" prop="checkoutType" label-width="100">
              <el-select v-model="checkoutAddData.checkoutType" clearable placeholder="请选择">
                <el-option
                        v-for="item in checkoutTypeS"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>

          <el-row>
            <el-form-item label="退住原因：" prop="checkoutReason" label-width="100">
              <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="请输入原因"
                      v-model="checkoutAddData.checkoutReason">
              </el-input>
            </el-form-item>
          </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="checkoutAddVisible = false">取 消</el-button>
          <el-button type="primary" @click="addCheckout">确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog title="退住详情" :visible.sync="checkoutDetailsVisible" width="35%">
        <el-form
                disabled
                ref="checkoutDetailsData"
                size="mini"
                style="text-align:left"
                :model="checkoutDetailsData"
                :inline="true"
                label-width="100px"
        >
          <el-form-item label="退住id：" label-width="100">
            <el-input v-model="checkoutDetailsData.id"></el-input>
          </el-form-item>
          <el-form-item label="创建时间：">
            <el-input v-model="checkoutDetailsData.createTime"></el-input>
          </el-form-item>
          <el-form-item label="创建者：" label-width="100">
            <el-input v-model="checkoutDetailsData.createBy"></el-input>
          </el-form-item>
          <el-form-item label="更新时间：">
            <el-input v-model="checkoutDetailsData.updateTime"></el-input>
          </el-form-item>
          <el-form-item label="更新者：" label-width="100">
            <el-input v-model="checkoutDetailsData.updateBy"></el-input>
          </el-form-item>
          <el-form-item label="客户id：">
            <el-input v-model="checkoutDetailsData.cusId"></el-input>
          </el-form-item>
          <el-form-item label="客户名：" label-width="100">
            <el-input v-model="checkoutDetailsData.customer.cusName"></el-input>
          </el-form-item>
          <el-form-item label="楼层：">
            <el-input v-model="checkoutDetailsData.bed.floor"></el-input>
          </el-form-item>
          <el-form-item label="房间号码：" label-width="100">
            <el-input v-model="checkoutDetailsData.bed.roomNo"></el-input>
          </el-form-item>
          <el-form-item label="床位号：">
            <el-input v-model="checkoutDetailsData.bed.bedNo"></el-input>
          </el-form-item>
          <el-form-item label="退住日期：" label-width="100">
            <el-input v-model="checkoutDetailsData.checkoutDate"></el-input>
          </el-form-item>
          <el-form-item label="退住类型：">
            <el-select v-model="checkoutDetailsData.checkoutType" placeholder="请选择">
              <el-option
                      v-for="item in checkoutTypeS"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="退住状态：" label-width="100">
            <el-select v-model="checkoutDetailsData.status" placeholder="请选择">
              <el-option
                      v-for="item in statusS"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="审核人：">
            <el-input v-model="checkoutDetailsData.auditPerson"></el-input>
          </el-form-item>
          <el-row>
            <el-form-item label="审核情况：" label-width="100">
              <el-radio-group v-model="checkoutDetailsData.audit">
                <el-radio :label="0">未审核</el-radio>
                <el-radio :label="1">审核通过</el-radio>
                <el-radio :label="2">审核不通过</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-row>
          <el-form-item label="审核时间：" label-width="100">
            <el-input v-model="checkoutDetailsData.auditTime"></el-input>
          </el-form-item>
          <el-row>
            <el-form-item label="退住原因：" label-width="100">
              <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="请输入原因"
                      v-model="checkoutDetailsData.checkoutReason">
              </el-input>
            </el-form-item>
          </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="checkoutDetailsVisible = false">取 消</el-button>
        </span>
      </el-dialog>

      <el-dialog title="编辑退住信息" :visible.sync="checkoutEditVisible" width="35%">
        <el-form
                ref="checkoutEditData"
                size="mini"
                style="text-align:left"
                :model="checkoutEditData"
                :inline="true"
                label-width="100px"
        >
          <!--<el-form-item label="退住id：" label-width="100">
            <el-input v-model="checkoutEditData.id" disabled></el-input>
          </el-form-item>
          <el-form-item label="创建时间：">
            <el-input v-model="checkoutEditData.createTime" disabled></el-input>
          </el-form-item>
          <el-form-item label="创建者：" label-width="100">
            <el-input v-model="checkoutEditData.createBy" disabled></el-input>
          </el-form-item>
          <el-form-item label="更新时间：">
            <el-input v-model="checkoutEditData.updateTime" disabled></el-input>
          </el-form-item>
          <el-form-item label="更新者：" label-width="100">
            <el-input v-model="checkoutEditData.updateBy" disabled></el-input>
          </el-form-item>
          <el-form-item label="客户id：">
            <el-input v-model="checkoutEditData.cusId" disabled></el-input>
          </el-form-item>-->
          <el-form-item label="客户名：" label-width="100">
            <el-input v-model="checkoutEditData.customer.cusName" disabled></el-input>
          </el-form-item>
          <el-form-item label="楼层：">
            <el-input v-model="checkoutEditData.bed.floor" disabled></el-input>
          </el-form-item>
          <el-form-item label="房间号码：" label-width="100">
            <el-input v-model="checkoutEditData.bed.roomNo" disabled></el-input>
          </el-form-item>
          <el-form-item label="床位号：">
            <el-input v-model="checkoutEditData.bed.bedNo" disabled></el-input>
          </el-form-item>
          <el-form-item label="退住日期：" label-width="100">
            <el-input v-model="checkoutEditData.checkoutDate"></el-input>
          </el-form-item>
          <el-form-item label="退住类型：">
            <el-select v-model="checkoutEditData.checkoutType" placeholder="请选择">
              <el-option
                      v-for="item in checkoutTypeS"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <!--<el-form-item label="退住状态：" label-width="100">
            <el-select v-model="checkoutEditData.status" placeholder="请选择">
              <el-option
                      v-for="item in statusS"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="审核人：">
            <el-input v-model="checkoutEditData.auditPerson"></el-input>
          </el-form-item>
          <el-row>
            <el-form-item label="审核情况：" label-width="100">
              <el-radio-group v-model="checkoutEditData.audit">
                <el-radio :label="0">未审核</el-radio>
                <el-radio :label="1">审核通过</el-radio>
                <el-radio :label="2">审核不通过</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-row>
          <el-form-item label="审核时间：" label-width="100">
            <el-date-picker
                    v-model="checkoutEditData.auditTime"
                    type="datetime"
                    placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>-->
          <el-row>
            <el-form-item label="退住原因：" label-width="100">
              <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="请输入原因"
                      v-model="checkoutEditData.checkoutReason">
              </el-input>
            </el-form-item>
          </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="checkoutEditVisible = false">取 消</el-button>
          <el-button @click="editSumit" type="primary">确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog title="退住审核" :visible.sync="checkoutAuditVisible" width="25%">
        <el-form
                :rules="rulesAudit"
                ref="checkoutAuditData"
                size="mini"
                style="text-align:left"
                :model="checkoutAuditData"
                :inline="true"
                label-width="100px"
        >
          <el-form-item label="审核人：" prop="auditPerson">
            <el-input v-model="checkoutAuditData.auditPerson"></el-input>
          </el-form-item>
          <br>
          <el-form-item label="审核情况：" prop="audit">
            <el-radio-group v-model="checkoutAuditData.audit">
              <el-radio :label="1">审核通过</el-radio>
              <el-radio :label="2">审核不通过</el-radio>
            </el-radio-group>
          </el-form-item>
          <br>
          <el-form-item label="审核时间：" prop="auditTime">
            <el-date-picker
                    v-model="checkoutAuditData.auditTime"
                    type="datetime"
                    placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="checkoutAuditVisible = false">取 消</el-button>
          <el-button @click="auditSumit" type="primary">确 定</el-button>
        </span>
      </el-dialog>
    </el-main>
  </el-container>

</template>

<script>

  import qs from "qs";

  export default {
    name: "Checkout",
    created(){
      this.getTableData(1);
    },
    methods: {
      statusF(row){
        switch(row.status){
          case 0: return "处理中";
          case 1: return "已退住";
        }
      },

      auditF(row){
        switch(row.audit){
          case 0: return "未审核";
          case 1: return "审核通过";
          case 2: return "审核未通过";
          default:return "未审核";
        }
      },

      isEnableCheckoutEdit(row){
        if (row.audit != 0) {
          return true
        } else {
          return false
        }
      },

      isEnableCheckout(row){
        if (row.audit == 1 && row.status == 0) {
          return false
        } else {
          return true
        }
      },

      async addOpen(){
        // this.checkoutAddData.bedInfo = "",
        await this.$axios("api/checkout/getCheckoutAddVoList").then(res => {
          this.checkoutAddVoList = res.data;
        });

        this.checkoutData.forEach(x => {
          this.checkoutAddVoList.forEach(y => {
            if (y.cusId == x.cusId){
              y.disabled = true;
            }
          })
        });
        this.checkoutAddVisible = true;
        // this.$refs.checkoutAddData.resetFields();
      },

      addCheckout(){
        this.$refs.checkoutAddData.validate(valid => {
          if (valid){
            this.checkoutAddData.createBy = this.userName;
            this.$axios.post("api/checkout/addCheckout",qs.stringify(this.checkoutAddData)).then(res => {
              if (res.data.code == 200){
                this.$message({
                  type: "success",
                  message: res.data.msg
                });
                this.checkoutAddVisible = false;
                this.checkoutAddData.cusId = "";
                this.checkoutAddData.bedInfo = [];
                this.checkoutAddData.checkoutDate = "";
                let num = parseInt(this.page.totalCount/this.page.pageSize)+1;
                this.getTableData(num);
              } else {
                this.$message({
                  type: "error",
                  message: res.data.msg
                });
              }
            })
          }
        });
      },


      handleChange(value) {
        console.log(value);
      },

      details(row){
        // console.log(JSON.stringify(row));
        this.checkoutDetailsData = row;
        if (this.checkoutDetailsData.audit == null || this.checkoutDetailsData.audit == ''){
          this.checkoutDetailsData.audit = 0;
        }
        this.checkoutDetailsVisible = true;
      },

      checkoutEdit(row){
        this.checkoutEditData = row;
        this.checkoutEditVisible = true;
      },

      editSumit(){
        this.$refs.checkoutEditData.validate(valid => {
          if (valid){
            this.checkoutEditData.updateBy = this.userName;
            this.checkoutEditData.cusName = this.checkoutEditData.customer.cusName;
            this.checkoutEditData.bedId = this.checkoutEditData.bed.id;
            this.checkoutEditData.floor = this.checkoutEditData.bed.floor;
            this.checkoutEditData.roomNo = this.checkoutEditData.bed.roomNo;
            this.checkoutEditData.bedNo = this.checkoutEditData.bed.bedNo;
            // let data = JSON.stringify(this.checkoutEditData);
            // console.log("editSumit>>>>"+data);return;
            // data.customer = this.checkoutEditData.customer;
            // data.bed = this.checkoutEditData.bed;
            this.$axios.post("api/checkout/checkoutEdit",qs.stringify(this.checkoutEditData)).then(res => {
              if (res.data.code == 200){
                this.$message({
                  type: "success",
                  message: res.data.msg
                });
                this.checkoutEditVisible = false;
                this.getTableData(this.page.currentPage);
              } else {
                this.$message({
                  type: "error",
                  message: res.data.msg
                });
              }
            });
          }
        })
      },

      checkoutAudit(row){
        this.checkoutAuditData = row;
        if (this.checkoutAuditData.audit == null || this.checkoutAuditData.audit == ''){
          this.checkoutAuditData.audit = 0;
        }
        this.checkoutAuditData.auditPerson = this.userName;
        this.checkoutAuditData.auditTime = new Date();
        this.checkoutAuditVisible = true;
        this.$refs.checkoutAuditData.resetFields();
      },

      auditSumit(){
        this.$refs.checkoutAuditData.validate(valid => {
          if (valid){
            this.$axios.post("api/checkout/checkoutAuditUpdate",qs.stringify({
              id: this.checkoutAuditData.id,
              auditPerson: this.checkoutAuditData.auditPerson,
              audit: this.checkoutAuditData.audit,
              auditTime: this.checkoutAuditData.auditTime
            })).then(res => {
              if (res.data.code == 200){
                this.$message({
                  type: "success",
                  message: res.data.msg
                });
                this.checkoutAuditVisible = false;
                this.getTableData(this.page.currentPage);
              } else {
                this.$message({
                  type: "error",
                  message: res.data.msg
                });
              }
            });
          }
        });
      },


      checkoutDo(row){
        if (row.status == 1 || row.status == '1'){
          this.$message({
            type: "success",
            message: "已退住"
          })
          return;
        }
        this.$axios.post("api/checkout/checkoutDo",qs.stringify({
          id: row.id,
          status: 1
        })).then(res => {
          if (res.data.code == 200){
            this.$message({
              type: "success",
              message: res.data.msg
            });
            this.getTableData(this.page.currentPage);
          } else {
            this.$message({
              type: "error",
              message: res.data.msg
            });
          }
        });
      },

      delCheckout(id){
        this.$axios.delete("api/checkout/delCheckout",{
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

      selectByLike() {
        if (this.checkoutForm.cusName==null||this.checkoutForm.cusName==""){
          this.getTableData(this.page.currentPage);
          return;
        }
        if (this.isSelectByLike == false){
          this.isSelectByLike = true;
          this.page.currentPage = 1;
        }
        this.$axios('api/checkout/selectByLike', {
          params: {
            currentPage: this.page.currentPage,
            pageSize: this.page.pageSize,
            cusName: this.checkoutForm.cusName
          }
        }).then(res => {
          this.checkoutData = res.data.data.records;
          this.page.totalCount = res.data.data.length;
        });
      },

      //复选框事件
      handleSelectionChange(val) {
        // console.log(val);
        this.batchAuditIds = [];
        val.forEach((row) => {
          if (row.moveAudit == "0") {
            this.batchAuditIds.push(row.moveId);
          }
        });
        this.tableChecked = val;
      },

      delBatch(){
        this.$confirm("是否撤销所选信息?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.ids = [];
          this.tableChecked.forEach((row) => {
            this.ids.push(row.id);
          });
          // console.log(this.ids);
          this.$axios({
            method: "delete",
            url: "api/checkout/delSelected",
            headers: {
              "Content-Type": "application/json;charset=UTF-8",
            },
            data: JSON.stringify(this.ids),
          }).then((result) => {
            // console.log('result: '+result.data.code);
            if (result.data.code == 200) {
              this.$message({
                type: "success",
                message: result.data.msg,
              });
              console.log('(this.page.totalCount-this.ids.length)%this.page.pageSize==0?'+((this.page.totalCount-this.ids.length)%this.page.pageSize==0));
              console.log('this.page.currentPage!=1?'+this.page.currentPage!=1)
              if (((this.page.totalCount-this.ids.length)%this.page.pageSize==0)&&(this.page.currentPage!=1)){
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
        });
      },

      //pageSize改变调用
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },

      getTableData(pageNo) {
        let self = this
        self.$axios('api/checkout/getCheckoutList', {
          params: {
            pageSize: 10,
            currentPage: pageNo
          }
        }).then(res => {
          if (res.data.code == 200) {
            self.checkoutData = res.data.data.records;
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
      var validateAudit = (rule, value, callback) => {
        if (value == 0 || value == '0' || value == null || value == '') {
          callback(new Error('审核情况不能为空'));
        } else {
          callback();
        }
      };
      return{
        rulesAdd: {
          cusId: [
            { required: true, message: "退住人信息不能为空", trigger: "blur" },
          ],
          checkoutDate: [
            { required: true, message: "退住日期不能为空", trigger: "blur" },
          ],
          checkoutType: [
            { required: true, message: "退住类型不能为空", trigger: "blur" },
          ],
          checkoutReason: [
            { required: true, message: "退住原因不能为空", trigger: "blur" },
          ],
        },

        rulesAudit: {
          auditPerson: [
            { required: true, message: "审核人不能为空", trigger: "blur" },
          ],
          audit: [
            { validator: validateAudit, trigger: "blur" },
          ],
          auditTime: [
            { required: true, message: "审核时间不能为空", trigger: "blur" },
          ],
        },

        checkoutForm: {
          cusName: ""
        },

        value: "",

        checkoutAddVoList: [],

        checkoutTypeS: [
          {
           value: 0,
            label: "回家"
          },
          {
            value: 1,
            label: "逝世"
          },
          {
            value: 2,
            label: "换院"
          }
        ],

        statusS: [
          {
            value: 0,
            label: "处理中"
          },
          {
            value: 1,
            label: "已退住"
          }
        ],

        checkoutAddVisible: false,
        checkoutDetailsVisible: false,
        checkoutEditVisible: false,
        checkoutUpdateVisible: false,
        checkoutAuditVisible: false,

        ids: [],
        tableChecked: [], //选中显示的值
        checkoutData: [],
        checkoutAddData: [],
        checkoutDetailsData: {
          customer: {
            cusName: ""
          },
          bed: {
           floor: "",
           roomNo: "",
           bedNo: ""
          }
        },
        checkoutEditData: {
          customer: {
            cusName: ""
          },
          bed: {
            floor: "",
            roomNo: "",
            bedNo: ""
          }
        },
        checkoutAuditData: [],
        page: {
          currentPage: 1, // 当前页
          pageSize: 10, // 每页显示条目个数
          totalCount: 0 // 总条目数
        },
        // 表格高度
        tableHeight: window.innerHeight - 280,
        userName: JSON.parse(sessionStorage.getItem("user")).userName
      };
    }
  }
</script>

<style scoped>

</style>