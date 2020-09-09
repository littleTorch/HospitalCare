<template>
    <el-main>
    <!--搜索框-->
    <el-form size="mini" :model="enterFrom" label-width="80px">
      <el-row>
        <el-col :span="5">
          <el-form-item label="学号:">
            <el-input v-model="enterFrom.enterStuNo" placeholder="请输入学号"></el-input>
          </el-form-item>
        </el-col>
        <el-button @click="selectByLike(enterFrom.enterStuNo)"  style="margin-left: 20px;" class="btn-left" size="mini" type="primary" icon="el-icon-search">查询</el-button>
        <el-button @click="addOneEnter" size="mini" type="primary" icon="el-icon-plus">新增入宿</el-button>
        <el-button @click="addManyEnter" size="mini" type="primary" icon="el-icon-upload">批量导入</el-button>
        <el-button @click="dow" size="mini" type="primary" icon="el-icon-download">批量导入模板下载</el-button>
        <el-button @click="passList(tableChecked)" size="mini" type="primary" icon="el-icon-upload" >批量审核</el-button>
        <el-button @click="deleteList(tableChecked)" size="mini" type="primary" icon="el-icon-delete">批量撤销</el-button>
        
      </el-row>
    </el-form>
    <!--数据表格-->
    <el-table ref="enterData" tooltip-effect="dark" :data="enterData.slice((currentPage-1) * pageSize ,currentPage * pageSize)" @selection-change="handleSelectionChange" size="mini" :height="tableHeight" border style="width: 100%">
      <el-table-column align="center" type="selection" width="43"></el-table-column>
      <el-table-column align="center" prop="enterStuNo" label="学号" ></el-table-column>
      <el-table-column align="center" prop="enterStuName" label="姓名" ></el-table-column>
      <el-table-column align="center" prop="enterBld" label="申请宿舍楼" ></el-table-column>
      <el-table-column align="center" prop="enterRoom" label="申请宿舍号" ></el-table-column>
      <el-table-column align="center" prop="enterReason" label="申请原因" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" :formatter="stateFormat" prop="enterAudit" label="审核情况" width="110"></el-table-column>
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button @click="pass(scope.row)" type="primary" :disabled="scope.row.enterAudit == 1 ? true : false" size="mini">通过审核</el-button>
          <el-button @click="details(scope.row.enterId)" type="primary" size="mini">详情</el-button>
          <el-button @click="findOne(scope.row.enterId)" type="success" size="mini">编辑</el-button>
            <el-popconfirm
                    style="margin-left: 10px;"
                    title="您确定撤销吗？"
                    icon="el-icon-info"
                    iconColor="red"
                    @onConfirm="deleteOne(scope.row.enterId)"
            >
                <el-button size="mini" type="danger" slot="reference">撤销</el-button>
            </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件
      size-change: 当page-sizes 改变时触发事件  
      current-change：当页数发生变化时触发事件，  
      current-page：当前是第几页  
      page-size：页容量，也就是每页多少条数据  
      total:总共有多少条数据,后台返回数据数值
    -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-size="pageSize"
      layout="total, prev, pager, next"
      :total="enterData.length"
    ></el-pagination>

    <el-dialog title="新增入宿表" :visible.sync="addOneEnterVisible" width="40%">
       <el-form size="mini" ref="addOne" style="text-align:center" :model="addOne" :inline="true" label-width="100px">
        <el-form-item label="学号">
          <el-input v-model="addOne.enterStuNo" placeholder="请输入学号"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="addOne.enterStuName" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="申请宿舍楼">
          <el-input v-model="addOne.enterBld" placeholder="请输入申请宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="申请宿舍号">
          <el-input v-model="addOne.enterRoom" placeholder="请输入申请宿舍号"></el-input>
        </el-form-item>
        <el-form-item label="申请原因">
          <el-input v-model="addOne.enterReason" placeholder="请输入申请原因"></el-input>
        </el-form-item>
        <br/>
        <el-form-item label="审核情况">
          <el-radio-group v-model="addOne.enterAudit">
            <el-radio :label="0">未审核</el-radio>
            <el-radio :label="1">通过审核</el-radio>
            <el-radio :label="2">未通过审核</el-radio>
          </el-radio-group>
          
        </el-form-item>
        
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addOneEnterVisible = false">取 消</el-button>
        <el-button @click="addOneSubmit()" type="primary" >确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改入宿表" :visible.sync="updateOneEnterVisible" width="40%">
       <el-form ref="updateOneEnter" :model="updateOneEnter" size="mini" style="text-align:center"  :inline="true" label-width="100px">
        <el-form-item label="学号">
          <el-input v-model="updateOneEnter.enterStuNo" placeholder="请输入学号"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="updateOneEnter.enterStuName" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="申请宿舍楼">
          <el-input v-model="updateOneEnter.enterBld" placeholder="请输入申请宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="申请宿舍号">
          <el-input v-model="updateOneEnter.enterRoom" placeholder="请输入申请宿舍号"></el-input>
        </el-form-item>
        <el-form-item label="申请原因">
          <el-input v-model="updateOneEnter.enterReason" placeholder="请输入申请原因"></el-input>
        </el-form-item>
        <br/>
        <el-form-item label="审核情况">
          <el-radio-group v-model="updateOneEnter.enterAudit">
            <el-radio :label="0">未审核</el-radio>
            <el-radio :label="1">通过审核</el-radio>
            <el-radio :label="2">未通过审核</el-radio>
          </el-radio-group>
          
        </el-form-item>
        
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateOneEnterVisible = false">取 消</el-button>
        <el-button @click="updateSubmit()" type="primary" >确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="批量导入" :visible.sync="addManyEnterVisible" width="30%" align="center">
      <el-upload
              class="upload-demo"
              drag
              multiple="fales"
              accept=".xls, .xlsx"
              :limit='2'
              :http-request="modeUpload"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">只能上传xls/xlsx文件，且不超过500kb</div>
      </el-upload>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addManyEnterVisible = false">取 消</el-button>
        <el-button type="primary"  @click="upload">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="入宿详情" :visible.sync="enterDetailsVisible" :data="enterDetails" width="40%">
            <el-form  :model="enterDetails" size="mini" style="text-align:center"  :inline="true" label-width="100px">
                <el-form-item label="学号">
                    <el-input v-model="enterDetails.enterStuNo" disabled></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="enterDetails.enterStuName" disabled></el-input>
                </el-form-item>
                <el-form-item label="申请宿舍楼">
                    <el-input v-model="enterDetails.enterBld" disabled></el-input>
                </el-form-item>
                <el-form-item label="申请宿舍号">
                    <el-input v-model="enterDetails.enterRoom" disabled></el-input>
                </el-form-item>
                <el-form-item label="申请原因">
                    <el-input v-model="enterDetails.enterReason" disabled></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="审核情况">
                    <el-radio-group v-model="enterDetails.enterAudit">
                        <el-radio :label="0">未审核</el-radio>
                        <el-radio :label="1">通过审核</el-radio>
                        <el-radio :label="2">未通过审核</el-radio>
                    </el-radio-group>

                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="enterDetailsVisible = false">取 消</el-button>
      </span>
        </el-dialog>


    </el-main>
</template>

<script>

export default {
  methods: {
    addOneEnter() {
       this.addOneEnterVisible = true;
    },
    addManyEnter() {
      this.addManyEnterVisible = true;
    },

    //功能模块

    stateFormat(row){
        if(row.enterAudit == 0){

          return "未审核"
        }else if(row.enterAudit == 1){
          return "通过审核"
        }else {
            return "未通过审核"
        }
    },
    getEnterList: function(){
          this.$axios({
              method: "get",
              url: "api/enter/enterList"
          }).then((result) =>{
              this.enterData = result.data;
              //console.log(this.enterData);
          })
      },
    selectByLike(enterStuNo) {
        //alert(enterStuNo);
          this.$axios({
              method: "get",
              url: "api/enter/selectByLike",
              params: {
                  enterStuNo: enterStuNo,
              }
          }).then((result) =>{
              this.enterData = result.data;
              //console.log(this.enterData);
          })
     },
    addOneSubmit(){
        this.$refs.addOne.validate((valid) => {
            if(valid){
                var data = new FormData();
                data.append('enterStuNo',this.addOne.enterStuNo);
                data.append('enterStuName',this.addOne.enterStuName);
                data.append('enterBld',this.addOne.enterBld);
                data.append('enterRoom',this.addOne.enterRoom);
                data.append('enterReason',this.addOne.enterReason);
                data.append('enterAudit',this.addOne.enterAudit);
                //console.log(data);
                this.$axios({
                    method: "post",
                    url: "api/enter/insertOneStu",
                    data: data
                }).then((result) => {
                    if (result.data.code == 200) {
                        this.$message({
                            type: "success",
                            duration: 1000,
                            message: result.data.msg,
                        });
                        this.addOneEnterVisible = false;
                        this.$refs.addOne.resetFields(); // 刷新表单
                        this.getEnterList();
                    } else {
                        this.$message({
                            type: "error",
                            duration: 1000,
                            message: result.data.msg,
                        });
                    }
                });

            }
        });
      },
    deleteOne(enterId){
          this.$axios({
              method: "get",
              url: "api/enter/deleteOne",
              params: {
                  enterId: enterId,
              }
          }).then((result) => {
                  if (result.data.code == 200) {
                      this.$message({
                          type: "success",
                          duration: 1000,
                          message: result.data.msg,
                      });
                      this.getEnterList();
                  } else {
                      this.$message({
                          type: "error",
                          duration: 1000,
                          message: result.data.msg,
                      });
                  }
          });
      },
    deleteList(){
          //console.log(this.tableChecked);
          this.$confirm('是否撤销所选信息?','提示',{
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
          }).then(()=>{
              this.tableChecked.forEach(id =>{
                  this.ids.push(id.enterId);
              });
              //console.log(this.ids);
              this.$axios({
                  method: "delete",
                  url: "api/enter/delete",
                  headers: {
                      'Content-Type': 'application/json;charset=UTF-8'
                  },
                  data: JSON.stringify(this.ids),
              }).then((result) => {
                  if (result.data.code == 200) {
                      this.$message({
                          type: "success",
                          duration: 1000,
                          message: result.data.msg,
                      });
                      this.getEnterList();
                      this.ids = [];
                  } else {
                      this.$message({
                          type: "error",
                          duration: 1000,
                          message: result.data.msg,
                      });
                  }
              });
          })
      },
    handleSelectionChange(val){
        // console.log(val);
        this.tableChecked = val;
    },
    findOne(enterId){
          this.updateOneEnterVisible = true;
          //console.log(enterId)
          this.$axios({
              method: "get",
              url: "api/enter/findOne",
              params: {
                  enterId: enterId,
              }
          }).then((result) =>{
              this.updateOneEnter = result.data;
              //console.log(this.updateCheckin);
          })
      },
    updateSubmit(){
          this.$refs.updateOneEnter.validate((valid) => {
              if(valid){
                  var data = new FormData();
                  data.append('enterId',this.updateOneEnter.enterId);
                  data.append('enterStuNo',this.updateOneEnter.enterStuNo);
                  data.append('enterStuName',this.updateOneEnter.enterStuName);
                  data.append('enterBld',this.updateOneEnter.enterBld);
                  data.append('enterRoom',this.updateOneEnter.enterRoom);
                  data.append('enterReason',this.updateOneEnter.enterReason);
                  data.append('enterAudit',this.updateOneEnter.enterAudit);
                  this.$axios({
                      method: "post",
                      url: "api/enter/updateOneStu",
                      data: data
                  }).then((result) => {
                      if (result.data.code == 200) {
                          this.$message({
                              type: "success",
                              duration: 1000,
                              message: result.data.msg,
                          });
                          this.updateOneEnterVisible = false;
                          this.getEnterList();
                      } else {
                          this.$message({
                              type: "error",
                              duration: 1000,
                              message: result.data.msg,
                          });
                      }
                  });
              }
          });
      },
    upload() {
       // console.log("aaaaa")
          const data = new FormData();
          data.append('file', this.mode);
          this.$axios({
              method: "post",
              url: "api/enter/upload",
              data: data,
              headers: {
                  'Content-Type': 'multipart/form-data'
              }
          }).then((result) => {
              if (result.data.code == 200) {
                  this.$message({
                      type: "success",
                      duration: 1000,
                      message: result.data.msg,
                  });
                  this.addManyEnterVisible = false;
                  this.getEnterList();
              }
          });

      },
    modeUpload: function(item) {
          // console.log(item.file);
          this.mode = item.file;
          console.log(item.file);
      },
    dow(){
          this.$axios({
              url: "api/enter/download",
              method: 'post',
              responseType: 'arraybuffer'
          }).then((res) => {
              let url = window.URL.createObjectURL(new Blob([res.data]));
              let a = document.createElement('a');
              a.setAttribute("download","EnterTest.xlsx");
              a.href = url;
              a.click();
          })

      },
    pass(row){
        let _row = row;
        this.passForm = Object.assign({}, _row);
        //console.log(this.passForm.enterAudit)
        if (this.passForm.enterAudit == 0){
            this.passForm.enterAudit = 1;
            var data = new FormData();
            data.append('enterId',this.passForm.enterId)
            data.append('enterStuNo',this.passForm.enterStuNo);
            data.append('enterStuName',this.passForm.enterStuName);
            data.append('enterBld',this.passForm.enterBld);
            data.append('enterRoom',this.passForm.enterRoom);
            data.append('enterReason',this.passForm.enterReason);
            data.append('enterAudit',this.passForm.enterAudit);
            this.$axios({
                method: "post",
                url: "api/enter/updateOneStu",
                data: data
            }).then((result) => {
                if (result.data.code == 200) {
                    this.$message({
                        type: "success",
                        duration: 1000,
                        message: result.data.msg,
                    });
                    this.getEnterList();
                } else {
                    this.$message({
                        type: "error",
                        duration: 1000,
                        message: result.data.msg,
                    });
                }
            });

        }

    },
    passList(){
          //console.log(this.tableChecked);
          this.$confirm('是否审核所选信息?','提示',{
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
          }).then(()=>{
              this.tableChecked.forEach(id =>{
                  this.ids.push(id.enterId);
              });
              //console.log(this.ids);
              this.$axios({
                  method: "delete",
                  url: "api/enter/passList",
                  headers: {
                      'Content-Type': 'application/json;charset=UTF-8'
                  },
                  data: JSON.stringify(this.ids),
              }).then((result) => {
                  if (result.data.code == 200) {
                      this.$message({
                          type: "success",
                          duration: 1000,
                          message: result.data.msg,
                      });
                      this.getEnterList();
                      this.ids = [];
                  } else {
                      this.$message({
                          type: "error",
                          duration: 1000,
                          message: result.data.msg,
                      });
                  }
              });
          })
      },
    details(enterId){
        this.enterDetailsVisible = true;
        console.log(enterId)
        this.$axios({
            method: "get",
            url: "api/enter/findOne",
            params: {
                enterId: enterId,
            }
        }).then((result) =>{
            this.enterDetails = result.data;
           // console.log(this.updateCheckin);
        })
    },

    //pageSize改变调用
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    // 页数改变调用
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
        this.currentPage = val;
    },
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
    },
  },
  data() {
    return {
      mode: {},
      pageSize: 10,
      currentPage: 1,
      addOneEnterVisible: false,
      addManyEnterVisible: false,
      updateOneEnterVisible: false,
      enterDetailsVisible: false,
      addOne: {},
      enterDetails: {},
      //搜索框数据绑定
      enterFrom: {
          enterStuNo: "",
      },
      updateOneEnter: [],
      // 表格高度
      tableHeight: 0,
      // 表格数据
      enterData: [],
      tableChecked: [],//选中显示的值
      ids:[],//批量删除id
      passForm: {},
    };
  },
  mounted() {
      this.$nextTick(() => {
          this.tableHeight = window.innerHeight - 285;
      });
  },
  created: function () {
       this.getEnterList();
  },
};
</script>

<style>
</style>