<template>
   <el-main>
      <!--搜索框-->
      <el-form size="mini" :model="From" label-width="80px">
         <el-row>
            <el-col :span="5">
               <el-form-item label="客户名:">
                  <el-input v-model="From.cusName" placeholder=""></el-input>
               </el-form-item>
            </el-col>
            <el-col :span="5">
               <el-form-item label="档案号:">
                  <el-input v-model="From.recordId" placeholder=""></el-input>
               </el-form-item>
            </el-col>
            <el-button
                    style="margin-left: 20px;"
                    class="btn-left"
                    size="mini"
                    type="primary"
                    icon="el-icon-search"
                    @click="sel(1)"
            >查询
            </el-button>
            <el-button @click="AddOne" size="mini" type="primary" icon="el-icon-plus">单个新增</el-button>
            <el-button @click="deleteList" size="mini" type="primary" icon="el-icon-delete">批量删除</el-button>
         </el-row>
      </el-form>
      <!--数据表格-->
      <el-table
              :data="customerData"
              size="mini"
              :height="tableHeight"
              @selection-change="handleSelectionChange"
              border
              style="width: 100%"
              tooltip-effect="dark"
      >
         <el-table-column align="center" type="selection"></el-table-column>
         <el-table-column align="center" prop="cusName" label="客户名"></el-table-column>
         <el-table-column align="center" prop="cusAge" label="年龄"></el-table-column>
         <el-table-column align="center" prop="expirationDate" label="合同到期日期"></el-table-column>
         <el-table-column align="center" prop="elderType" label="老人类型"></el-table-column>
         <el-table-column align="center" :formatter="typeFormat" prop="cusSex" label="性别"></el-table-column>
         <el-table-column label="操作" width="250" align="center">
            <template slot-scope="scope">
               <el-button @click="details(scope.row)" type="primary" size="mini">详情</el-button>
               <el-button @click="updateRoom(scope.row)" type="success" size="mini">编辑</el-button>
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

      <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="page.currentPage"
              :page-size="page.pageSize"
              :total="page.totalCount"
              layout="total, prev, pager, next"
      ></el-pagination>

      <el-dialog title="新增客户" :visible.sync="addOneVisible" width="50%">
         <el-form
                 ref="addOneForm"
                 size="mini"
                 style="text-align:center"
                 :model="addOneForm"
                 inline
                 label-width="120px"
         >
            <el-form-item label="客户名：">
               <el-input v-model="addOneForm.cusName" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="年龄：">
               <el-input v-model="addOneForm.cusAge" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="身份证号：">
               <el-input v-model="addOneForm.cusIdcard" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="老人类型：">
               <el-input v-model="addOneForm.elderType" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="联系电话：">
               <el-input v-model="addOneForm.phone" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="身心状况：">
               <el-input v-model="addOneForm.psychologicalStatus" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="注意：">
               <el-input v-model="addOneForm.attentin" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="身高：">
               <el-input v-model="addOneForm.height" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="体重：">
               <el-input v-model="addOneForm.weight" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="血型：">
               <el-input v-model="addOneForm.bloodType" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="合同到期日期：">
               <el-date-picker
                       v-model="addOneForm.expirationDate"
                       type="date"
                       placeholder="选择日期">
               </el-date-picker>
            </el-form-item>
            <el-form-item label="出生年月：">
               <el-date-picker
                       v-model="addOneForm.birthday"
                       type="date"
                       placeholder="选择日期">
               </el-date-picker>
            </el-form-item>
            <br/>
            <el-form-item label="性别：">
               <el-radio-group v-model="addOneForm.cusSex">
                  <el-radio :label="0">男</el-radio>
                  <el-radio :label="1">女</el-radio>
               </el-radio-group>
            </el-form-item>
            <br/>
            <el-form-item label="婚姻状态：">
               <el-radio-group v-model="addOneForm.maritalStatus">
                  <el-radio :label="0">已婚</el-radio>
                  <el-radio :label="1">丧偶/丧夫</el-radio>
                  <el-radio :label="2">未婚</el-radio>
               </el-radio-group>
            </el-form-item>
         </el-form>
         <span slot="footer" class="dialog-footer">
        <el-button @click="addOneVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOne">确 定</el-button>
      </span>
      </el-dialog>

      <el-dialog title="修改用户资料" :visible.sync="updateOneRoomVisible" width="50%">
         <el-form
                 size="mini"
                 style="text-align:center"
                 :model="updateOneForm"
                 :inline="true"
                 label-width="120px"
         >
            <el-form-item label="客户名：">
               <el-input v-model="updateOneForm.cusName" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="年龄：">
               <el-input v-model="updateOneForm.cusAge" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="身份证号：">
               <el-input v-model="updateOneForm.cusIdcard" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="老人类型：">
               <el-input v-model="updateOneForm.elderType" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="联系电话：">
               <el-input v-model="updateOneForm.phone" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="身心状况：">
               <el-input v-model="updateOneForm.psychologicalStatus" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="注意：">
               <el-input v-model="updateOneForm.attentin" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="身高：">
               <el-input v-model="updateOneForm.height" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="体重：">
               <el-input v-model="updateOneForm.weight" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="血型：">
               <el-input v-model="updateOneForm.bloodType" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="合同到期日期：">
               <el-date-picker
                       v-model="updateOneForm.expirationDate"
                       type="date"
                       placeholder="选择日期">
               </el-date-picker>
            </el-form-item>
            <el-form-item label="出生年月：">
               <el-date-picker
                       v-model="updateOneForm.birthday"
                       type="date"
                       placeholder="选择日期">
               </el-date-picker>
            </el-form-item>
            <br/>
            <el-form-item label="性别：">
               <el-radio-group v-model="updateOneForm.cusSex">
                  <el-radio :label="0">男</el-radio>
                  <el-radio :label="1">女</el-radio>
               </el-radio-group>
            </el-form-item>
            <br/>
            <el-form-item label="婚姻状态：">
               <el-radio-group v-model="updateOneForm.maritalStatus">
                  <el-radio :label="0">已婚</el-radio>
                  <el-radio :label="1">丧偶/丧夫</el-radio>
                  <el-radio :label="2">未婚</el-radio>
               </el-radio-group>
            </el-form-item>
         </el-form>
         <span slot="footer" class="dialog-footer">
        <el-button @click="updateOneRoomVisible = false">取 消</el-button>
        <el-button @click="setOne" type="primary">确 定</el-button>
      </span>
      </el-dialog>

      <el-dialog title="宿舍详情" :visible.sync="roomVisible" width="50%">
         <el-form
                 size="mini"
                 style="text-align:center"
                 :model="customer"
                 :inline="true"
                 label-width="120px"
                 disabled
         >
            <el-form-item label="客户名：">
               <el-input v-model="customer.cusName" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="年龄：">
               <el-input v-model="customer.cusAge" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="身份证号：">
               <el-input v-model="customer.cusIdcard" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="老人类型：">
               <el-input v-model="customer.elderType" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="联系电话：">
               <el-input v-model="customer.phone" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="身心状况：">
               <el-input v-model="customer.psychologicalStatus" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="注意：">
               <el-input v-model="customer.attentin" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="身高：">
               <el-input v-model="customer.height" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="体重：">
               <el-input v-model="customer.weight" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="血型：">
               <el-input v-model="customer.bloodType" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="合同到期日期：">
               <el-date-picker
                       v-model="customer.expirationDate"
                       type="date"
                       placeholder="选择日期">
               </el-date-picker>
            </el-form-item>
            <el-form-item label="出生年月：">
               <el-date-picker
                       v-model="customer.birthday"
                       type="date"
                       placeholder="选择日期">
               </el-date-picker>
            </el-form-item>
            <br/>
            <el-form-item label="性别：">
               <el-radio-group v-model="customer.cusSex">
                  <el-radio :label="0">男</el-radio>
                  <el-radio :label="1">女</el-radio>
               </el-radio-group>
            </el-form-item>
            <br/>
            <el-form-item label="婚姻状态：">
               <el-radio-group v-model="customer.maritalStatus">
                  <el-radio :label="0">已婚</el-radio>
                  <el-radio :label="1">丧偶/丧夫</el-radio>
                  <el-radio :label="2">未婚</el-radio>
               </el-radio-group>
            </el-form-item>
         </el-form>
         <span slot="footer" class="dialog-footer">
        <el-button @click="roomVisible = false">取 消</el-button>
        <el-button @click="roomVisible = false" type="primary">确 定</el-button>
      </span>
      </el-dialog>
   </el-main>
</template>

<script>
   export default {
      created() {
         this.sel(1);
      },
      methods: {
         //详情
         details(row){
            console.log(row);
            this.roomVisible=true;
            this.customer=row;
         },
         setOne(){
            this.$axios({
               method: "put",
               url: "api/customer/updateOne",
               data: JSON.stringify(this.updateOneForm),
               headers:{'Content-Type':'application/json;charsetset=UTF-8'}
            }).then((res) => {
               if (res.data.code == 200){
                  this.updateOneRoomVisible = false;
                  this.$message({
                     type: "success",
                     duration: 1000,
                     message: res.data.msg
                  })
                  this.sel(this.page.currentPage);
               } else {
                  this.$message({
                     type: "error",
                     duration: 1000,
                     message: res.data.msg
                  })
               }
            })
         },
         deleteList() {
            this.$confirm('是否删除所选信息?', '提示', {
               confirmButtonText: '确定',
               cancelButtonText: '取消',
               type: 'warning',
            }).then(() => {
               let ids = [];
               this.tableChecked.forEach(id => {
                  ids.push(id.id);
               });
               this.dele(ids);
            })
         },
         dele(ids){
            this.$axios({
               method: "delete",
               url: "api/customer/dels",
               headers: {
                  'Content-Type': 'application/json;charset=UTF-8'
               },
               data: JSON.stringify(ids),
            }).then(res => {
               if (res.data.code == 200){
                  this.$message({
                     type: "success",
                     message: res.data.msg
                  })
                  if (((this.page.totalCount-ids.length)%this.page.pageSize==0)&&(this.page.currentPage!=1)){
                     this.sel(this.page.currentPage-1);
                  } else {
                     this.sel(this.page.currentPage);
                  }
               } else {
                  this.$message({
                     type: "error",
                     message: res.data.msg
                  })
               }
            })
         },
         deleteOne(id) {
            let ids = [];
            ids.push(id);
            this.dele(ids);
         },
         addOne() {
            this.$refs.addOneForm.validate((valid) => {
               if (valid) {
                  this.$axios({
                     method: "post",
                     url: "api/customer/addOne",
                     data: JSON.stringify(this.addOneForm),
                     headers:{'Content-Type':'application/json;charsetset=UTF-8'}
                  }).then(res=>{
                     if (res.data.code == 200){
                        this.addOneVisible = false;
                        this.$message({
                           type: "success",
                           message: res.data.msg
                        })
                        this.sel(this.page.currentPage);
                     } else {
                        this.$message({
                           type: "error",
                           message: res.data.msg
                        })
                     }
                  })
               }
            });
         },
         sel(pageNo) {
            this.$axios({
               method: "get",
               url: "api/customer/selectCustomerList",
               params: {
                  cusName: this.From.cusName,
                  recordId: this.From.recordId,
                  pageSize: 10,
                  currentPage: pageNo,
               }
            }).then(res => {
               if (res.data.code == 200) {
                  this.customerData = res.data.data.records;
                  this.page.totalCount=res.data.data.total;
                  this.page.pageSize=res.data.data.size;
                  this.page.currentPage=res.data.data.current;
               }
            })
         },
         //弹出框
         AddOne() {
            this.addOneVisible = true;
         },

         updateRoom(row) {
            this.updateOneRoomVisible = true;
            this.updateOneForm = Object.assign({}, row);
         },
         //表格格式
         typeFormat(row) {
            switch (row.cusSex) {
               case 0:
                  return "男";
               case 1:
                  return "女";
            }
         },
         //表格前的复选框点击事件
         handleSelectionChange(val) {
            this.tableChecked = val;
         },
         //pageSize改变调用
         handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
         },
         // 页数改变调用
         handleCurrentChange(val) {
            this.sel(val)
         },
         //重置表单内容
         resetForm(formName) {
            if (this.$refs[formName]) {
               this.$refs[formName].resetFields();
            }
         },
      },
      mounted() {
         this.$nextTick(() => {
            this.tableHeight = window.innerHeight - 280;
         });
      },
      data() {
         return {
            customer:{
               cusName: '',
               cusAge: '',
               cusIdcard: '',
               elderType: '',
               phone: '',
               psychologicalStatus:'',
               attentin:'',
               height:'',
               weight:'',
               bloodType:'',
               expirationDate:'',
               birthday:'',
               cusSex:0,
               maritalStatus:0
            },
            roomVisible:false,
            tableChecked: [],
            addOneVisible: false,
            updateOneRoomVisible: false,
            addOneForm: {
               cusName: '',
               cusAge: '',
               cusIdcard: '',
               elderType: '',
               phone: '',
               psychologicalStatus:'',
               attentin:'',
               height:'',
               weight:'',
               bloodType:'',
               expirationDate:'',
               birthday:'',
               cusSex:0,
               maritalStatus:0
            },
            //搜索框数据绑定
            From: {
               cusName: "",
               recordId:'',
            },
            updateOneForm: {
               cusName: '',
               cusAge: '',
               cusIdcard: '',
               elderType: '',
               phone: '',
               psychologicalStatus:'',
               attentin:'',
               height:'',
               weight:'',
               bloodType:'',
               expirationDate:'',
               birthday:'',
               cusSex:0,
               maritalStatus:0
            },
            // 表格高度
            tableHeight: 0,
            // 表格数据
            customerData: [],
            page: {
               currentPage: 1, // 当前页
               pageSize: 10, // 每页显示条目个数
               totalCount: 0 // 总条目数
            },
         };
      },
   };
</script>

<style>
</style>