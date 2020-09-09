<template>
    <el-main>
    <!--搜索框-->
    <el-form size="mini" :model="exitFrom" label-width="80px">
      <el-row>
        <el-col :span="5">
          <el-form-item label="名称:">
            <el-input v-model="exitFrom.exitStuName" placeholder="请输入申请人姓名"></el-input>
          </el-form-item>
        </el-col>
        <el-button @click="selectByLike(exitFrom.exitStuName)" style="margin-left: 20px;" class="btn-left" size="mini" type="primary" icon="el-icon-search">查询</el-button>
        <el-tooltip class="item" effect="dark" content="点击即可查询" placement="bottom">
           <el-button @click="graduateSelect" class="msg btn-left" size="mini" type="primary" icon="el-icon-search">毕业生查询</el-button>
        </el-tooltip>
        <el-button @click="addOneExit" size="mini" type="primary" icon="el-icon-plus">退宿申请</el-button>
        <el-button @click="dow" size="mini" type="primary" icon="el-icon-download">批量导入模板下载</el-button>
        <el-button @click="addManyExit" size="mini" type="primary" icon="el-icon-plus">批量申请</el-button>
        <el-tooltip class="item" effect="dark" content="点击批量通过审核" placement="bottom">
            <el-button
                    @click="auditListY(tableChecked)"
                    size="mini" type="primary"
                    icon="el-icon-edit-outline"
            >批量通过审核</el-button>
        </el-tooltip>
          <el-tooltip class="item" effect="dark" content="点击批量不通过审核" placement="bottom">
              <el-button
                      @click="auditListN(tableChecked)"
                      size="mini"
                      type="primary"
                      icon="el-icon-edit-outline"
              >批量未通过审核</el-button>
          </el-tooltip>
        <el-button
                @click="deleteList(tableChecked)"
                size="mini" type="primary"
                icon="el-icon-delete"
        >批量撤销</el-button>
      </el-row>
    </el-form>
    <!--数据表格-->
    <el-table
            :data="exitData.slice((currentPage-1) * pageSize ,currentPage * pageSize)"
            ref="exitData"
            size="mini"
            @selection-change="handleSelectionChange"
            :height="tableHeight"
            border
            style="width: 100%"
    >
      <el-table-column align="center" type="selection" width="43"></el-table-column>
      <el-table-column align="center" prop="exitStuNo" label="学号" ></el-table-column>
      <el-table-column align="center" prop="exitStuName" label="姓名" ></el-table-column>
      <el-table-column align="center" :formatter="auditFormat" prop="exit_audit" label="审核情况" width="200"></el-table-column>
      
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button
                  @click="auditExit(scope.row)"
                  type="primary"
                  size="mini"
                  :disabled="scope.row.exitAudit == 0? false: true"
          >审核</el-button>
          <el-button @click="details(scope.row)" type="primary" size="mini">详情</el-button>
          <el-button @click="updateExit(scope.row)" type="success" size="mini" :disabled="scope.row.exitAudit == 0? false: true">编辑</el-button>
          <el-popconfirm
                    style="margin-left: 10px;"
                    title="您确定删除吗？"
                    icon="el-icon-info"
                    iconColor="red"
                    @onConfirm="deleteOne(scope.row.exitId)"
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
       :total="exitData.length"
    ></el-pagination>

        <!--申请模态框-->
    <el-dialog title="退宿申请表" :visible.sync="addOneExitVisible" width="25%">
       <el-form
               align="center"
               class="addOne"
               ref="addOne"
               size="mini"
               :model="addOne"
               :inline="true"
               label-width="100px"
               :rules="rules">
        <el-form-item label="学号" prop="exitStuNo">
          <el-input v-model="addOne.exitStuNo" placeholder="请输入学号"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="exitStuName">
          <el-input v-model="addOne.exitStuName" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="退宿宿舍楼" prop="exitBld">
          <el-input v-model="addOne.exitBld" placeholder="请输入退宿宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="退宿宿舍号" prop="exitRoom">
<!--          <el-input v-model="addOne.exitRoom" placeholder="请输入退宿宿舍号"></el-input>-->
            <el-select v-model="addOne.exitRoom" placeholder="请选择退宿房间号">
                <el-option
                        v-for="item in room"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                ></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="退宿原因" prop="exitReason">
          <el-input type="textarea" v-model="addOne.exitReason" placeholder="请输入退宿原因"></el-input>
        </el-form-item>
        <br/>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addOneSubmit()" type="primary" >确 定</el-button>
        <el-button @click="addOneExitVisible = false">取消</el-button>
      </span>
    </el-dialog>

    <!--详情模态框-->
    <el-dialog title="退宿申请详情" :visible.sync="exitDetailsVisible" width="25%">
            <el-form
                    class="exitDetails"
                    ref="exitDetails"
                    size="mini"
                    style="text-align:center"
                    :model="exitDetails"
                    :inline="true"
                    label-width="80px">
                <el-form-item label="学号">
                    <el-input v-model="exitDetails.exitStuNo" disabled></el-input>
                </el-form-item>
                <el-form-item label="学生姓名">
                    <el-input  v-model="exitDetails.exitStuName" disabled></el-input>
                </el-form-item>
                <el-form-item label="宿舍楼">
                    <el-input  v-model="exitDetails.exitBld" disabled></el-input>
                </el-form-item>
                <el-form-item label="房间号">
                    <el-input  v-model="exitDetails.exitRoom" disabled></el-input>
                </el-form-item>
                <el-form-item label="退宿原因" >
                    <el-input type="textarea" v-model="exitDetails.exitReason" disabled></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="exitDetailsVisible = false">返 回</el-button>
            </span>
        </el-dialog>

    <!--修改模态框-->
    <el-dialog title="修改退宿申请" :visible.sync="updateExitVisible" width="25%">
       <el-form
               class="updateData"
               ref="updateData"
               size="mini"
               style="text-align:center"
               :model="updateData"
               :inline="true"
               label-width="100px">
        <el-form-item label="学号">
          <el-input v-model="updateData.exitStuNo" placeholder="请输入学号"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="updateData.exitStuName" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="退宿宿舍楼">
          <el-input v-model="updateData.exitBld" placeholder="请输入退宿宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="退宿宿舍号">
<!--          <el-input v-model="updateData.exitRoom" placeholder="请输入退宿宿舍号"></el-input>-->
            <el-select v-model="updateData.exitRoom" placeholder="请选择退宿房间号">
                <el-option
                        v-for="item in room"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                ></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="退宿原因">
          <el-input v-model="updateData.exitReason" type="textarea" placeholder="请输入退宿原因"></el-input>
        </el-form-item>
        <br/>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateExitVisible = false">取 消</el-button>
        <el-button @click="updateSubmit()" type="primary" >确 定</el-button>
      </span>
    </el-dialog>

    <!--审核模态框-->
    <el-dialog title="审核" :visible.sync="auditVisible" width="30%">
        <el-form
                class="auditData"
                ref="auditData"
                size="mini"
                style="text-align:center"
                :model="auditData"
                :inline="true"
                label-width="100px">
            <el-form-item label="退宿原因">
                <el-input type="textarea" v-model="auditData.exitReason" disabled></el-input>
            </el-form-item>
            <el-form-item label="审核结果" >
                <el-radio-group v-model="auditData.exitAudit">
                    <el-radio :label="1">审核通过</el-radio>
                    <el-radio :label="2">审核未通过</el-radio>
                </el-radio-group>
            </el-form-item>
            <br/>
        </el-form>
        <span slot="footer" class="dialog-footer">
        <el-button @click="auditVisible = false">取 消</el-button>
        <el-button @click="auditSubmit()" type="primary" >确 定</el-button>
      </span>
    </el-dialog>

        <!--批量-->
    <el-dialog
      title="批量申请"
      :visible.sync="addManyExitVisible"
      width="30%"
      align="center"
    >
      <el-upload
        class="upload-demo"
        drag
        multiple="fales"
        :action="action"
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
        <el-button @click="addManyExitVisible = false">取 消</el-button>
        <el-button type="primary" @click="upload">确 定</el-button>
      </span>
    </el-dialog>

  </el-main>
</template>

<script>
export default {
  created: function(){
    this.getExitList();
    this.roomNoList();
  },
  methods: {
      //模态框
    details(row){
        this.exitDetails = row;
        this.exitDetailsVisible = true;
    },
    addOneExit() {
       this.addOneExitVisible = true;
    },
    addManyExit() {
      this.addManyExitVisible = true;
    },
    updateExit(row){
          this.updateData = row;
          this.updateExitVisible = true;
      },
    auditExit(row){
        this.auditData.exitId = row.exitId;
        this.auditData.exitReason = row.exitReason;
        this.auditVisible = true;
    },
      //功能
    getExitList: function(){
          this.$axios({
              method: "get",
              url: "api/exits/exitsList"
          }).then((result) =>{
              this.exitData = result.data;
              console.log(this.exitData);
          })
    },
      roomNoList(){
          this.$axios({
              method: "get",
              url: "api/room/roomList"
          }).then((result) => {
              let roomList = result.data;
              for (let i=0;i<roomList.length;i++){
                  let roomNo={
                      value:roomList[i].roomNo,
                      label:roomList[i].roomBld+roomList[i].roomNo,
                  };
                  this.room.push(roomNo);
              }
          })
      },
    graduateSelect(){
          this.$axios({
              method: "get",
              url: "api/exits/graduateSelect"
          }).then((result) =>{
              this.exitData = result.data;
              console.log(this.exitData);
          })
      },
    selectByLike(exitStuName) {
        if(exitStuName ==null){
            this.getExitList();
        }else{
          this.$axios({
              method: "get",
              url: "api/exits/selectByLike",
              params: {
                  exitStuName: exitStuName,
              }
          }).then((result) =>{
              this.exitData = result.data;
              console.log(this.exitData);
          })
        }
      },

    addOneSubmit(){
          this.$refs.addOne.validate((valid) => {
              if(valid){
                  var data = new FormData();
                  data.append('exitStuNo',this.addOne.exitStuNo);
                  data.append('exitStuName',this.addOne.exitStuName);
                  data.append('exitBld',this.addOne.exitBld);
                  data.append('exitRoom',this.addOne.exitRoom);
                  data.append('exitReason',this.addOne.exitReason);
                  this.$axios({
                      method: "post",
                      url: "api/exits/insertOneExit",
                      data: data
                  }).then((result)=>{
                      if (result.data.code ==200){
                          this.$message({
                              type: "success",
                              duration: 1000,
                              message: result.data.msg,
                          });
                          this.addOneExitVisible = false;
                          this.$refs.addOne.resetFields();
                          this.getExitList();
                      } else{
                          this.$message({
                              type: "error",
                              duration: 1000,
                              message: result.data.msg,
                          });
                      }
                  })
              }
          });
      },

    updateSubmit(){
        this.$refs.updateData.validate((valid) => {
            if(valid){
                var data = new FormData();
                data.append('exitId',this.updateData.exitId)
                data.append('exitStuNo',this.updateData.exitStuNo);
                data.append('exitStuName',this.updateData.exitStuName);
                data.append('exitBld',this.updateData.exitBld);
                data.append('exitRoom',this.updateData.exitRoom);
                data.append('exitReason',this.updateData.exitReason);
                this.$axios({
                    method: "post",
                    url: "api/exits/updateExit",
                    data: data
                }).then((result)=>{
                    if (result.data.code == 200) {
                        this.$message({
                            type: "success",
                            duration: 1000,
                            message: result.data.msg,
                        });
                        this.updateExitVisible = false;
                        this.getExitList();
                    }else {
                        this.$message({
                            type: "error",
                            duration: 1000,
                            message: result.data.msg,
                        });
                    }
                })
            }
        });

    },
    auditSubmit(){
        this.$refs.auditData.validate((valid) => {
            if(valid){
                var data = new FormData();
                data.append('exitId',this.auditData.exitId)
                data.append('exitAudit',this.auditData.exitAudit);
                this.$axios({
                    method: "post",
                    url: "api/exits/updateExit",
                    data: data
                }).then((result)=>{
                    this.$message({
                        type: "success",
                        duration: 1000,
                        message: result.data.msg,
                    });
                    this.auditVisible = false;
                    this.getExitList();
                })
            }
        });
    },
    auditListY(){
        if(this.tableChecked.length ===0){
            alert("选择为空");
        }else{
            this.$confirm('是否确认所选人员通过审核','提示',{
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(()=>{
                this.tableChecked.forEach(id =>{
                    if(id.exitAudit==0){
                        this.ids.push(id.exitId);
                    }
                });
                if(this.ids==0){
                    alert("您所选的消息已审核！")
                }else {
                    this.$axios({
                        method: "post",
                        url: "api/exits/updateListY",
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        },
                        data: JSON.stringify(this.ids),
                    }).then((result) => {
                        if(result.data.code ==200){
                            this.$message({
                                type: "success",
                                duration: 1000,
                                message: result.data.msg,
                            });
                            this.getExitList();
                        }else {
                            this.$message({
                                type: "error",
                                duration: 1000,
                                message: result.data.msg,
                            });
                        }
                    })
                }
            })
        }
    },
    auditListN(){
        if(this.tableChecked.length ===0){
            alert("选择为空");
        }else {
            this.$confirm('是否确认所选人员不通过审核', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(() => {
                this.tableChecked.forEach(id => {
                    if(id.exitAudit==0){
                        this.ids.push(id.exitId);
                    }
                });
                if(this.ids==0){
                    alert("您所选的消息已审核！")
                }else{
                    this.$axios({
                        method: "post",
                        url: "api/exits/updateListN",
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        },
                        data: JSON.stringify(this.ids),
                    }).then((result) => {
                        if(result.data.code ==200){
                            this.$message({
                                type: "success",
                                duration: 1000,
                                message: result.data.msg,
                            });
                            this.getExitList()
                        }else{
                            this.$message({
                                type: "error",
                                duration: 1000,
                                message: result.data.msg,
                            });
                        }
                    })
                }
            })
        }
      },

    deleteOne(id){
        this.$axios({
            method: "get",
            url: "api/exits/deleteOne",
            params: {
                exitId: id,
            }
        }).then((result)=>{
            if(result.data.code ==200){
                this.$message({
                    type: "success",
                    duration: 1000,
                    message: result.data.msg,
                });
                location.reload();
            }else {
                this.$message({
                    type: "error",
                    duration: 1000,
                    message: result.data.msg,
                });
            }
        })

    },
    deleteList(){
        if(this.tableChecked.length ===0){
            alert("选择为空");
        }else {
            // alert(this.tableChecked);
            //   console.log(this.tableChecked);
            this.$confirm('是否删除所选申请记录?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(() => {
                this.tableChecked.forEach(id => {
                    this.ids.push(id.exitId);
                });
                console.log(this.ids);
                this.$axios({
                    method: "delete",
                    url: "api/exits/delete",
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    },
                    data: JSON.stringify(this.ids),
                }).then((result) => {
                    if (result.data.code==200){
                        this.$message({
                            type: "success",
                            duration: 1000,
                            message: result.data.msg,
                        });
                        location.reload();
                    } else {
                        this.$message({
                            type: "error",
                            duration: 1000,
                            message: result.data.msg,
                        });
                    }
                })
            })
        }
      },

    //文件
    modeUpload: function(item) {
       // console.log(item.file);
       this.mode = item.file
       console.log(item.file);
    },
    upload(){
        const data = new FormData();
        data.append('file', this.mode);
        this.$axios({
            method: "post",
            url: "api/exits/upload",
            data: data,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).then((result) =>{
            if(result.data.code == 200){
                this.$message({
                    type: "success",
                    duration: 1000,
                    message: result.data.msg,
                });
                this.addManyExitVisible = false;
                this.getExitList();
            }else{
                alert("上传失败！");
                this.addManyExitVisible = false;
                this.getExitList();
            }
        })
    },
    //下载
    dow(){
          this.$axios({
              url: "api/exits/download",
              method: 'post',
              responseType: 'arraybuffer'
          }).then((res) => {
              let url = window.URL.createObjectURL(new Blob([res.data]))
              let a = document.createElement('a')
              a.setAttribute("download","Exits.xlsx")
              a.href = url
              a.click();
          })
      },

    //获取选项框的值
    handleSelectionChange(val){
          // console.log(val);
        this.tableChecked = val;
      },
    auditFormat(row){
          if(row.exitAudit == 1){
              return "审核通过";
          }else if(row.exitAudit == 2){
              return  "审核未通过";
          }else{
              return  "审核中";
          }
      },
    //pageSize改变调用
    handleSizeChange(val) {
      alert(`每页 ${val} 条`) ;
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
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 285;
    });
  },
  data() {
    return {
      pageSize:5,
      currentPage:1,
      action: 'https://jsonplaceholder.typicode.com/posts/',
      mode: {},
      addOneExitVisible: false,
      addManyExitVisible: false,
      updateExitVisible: false,
      exitDetailsVisible: false,
      auditVisible:false,
      addOne: {},
      //搜索框数据绑定
      exitFrom: {
        exit_id: "",
      },
      updateOneForm: [],
      // 表格高度
      tableHeight: window.innerHeight,
      // 表格数据
      exitData: [],
      exitDetails:[],
      updateData:[],
      auditData:[],
      tableChecked:[],
      ids:[],
      room:[],
      exitAuditIds: [],
      rules:{
          exitStuNo: [
              { required: true, message: "学号不能为空", trigger: "blur" },
          ],
          exitStuName: [
              { required: true, message: "姓名不能为空", trigger: "blur" },
          ],
          exitBld: [
              { required: true, message: "宿舍楼不能为空", trigger: "blur" },
          ],
          exitRoom: [
              { required: true, message: "宿舍号不能为空", trigger: "blur" },
          ],
          exitReason: [
              { required: true, message: "退宿原因不能为空", trigger: "blur" },
          ],
      }
    };
  },
};
</script>

<style>
</style>