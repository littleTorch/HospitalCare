<template>
  <el-main>

    <el-form size="mini" :model="stuFrom" label-width="80px">
      <el-row>
        <el-col :span="5">
          <el-form-item label="姓名:">
            <el-input v-model="stuFrom.stuName" placeholder="请输入学生姓名"></el-input>
          </el-form-item>
        </el-col>
        <el-button @click="selectByLike(stuFrom.stuName)" style="margin-left: 20px;" size="mini" type="primary" icon="el-icon-search">查询</el-button>
        <el-button @click="addOne" size="mini" type="primary" icon="el-icon-plus">单个新增</el-button>
        <el-button @click="addAll" size="mini" type="primary" icon="el-icon-upload">批量导入</el-button>
        <el-button @click="dow" size="mini" type="primary" icon="el-icon-download">批量导入模板下载</el-button>
        <el-button @click="deleteList(tableChecked)" size="mini" type="primary" icon="el-icon-delete">批量删除</el-button>

      </el-row>
    </el-form>

<!--    @selection-change="handleSelectionChange"-->
    <el-table
      ref="stuTable"
      :data="stuTable.slice((currentPage-1) * pageSize ,currentPage * pageSize)"
      tooltip-effect="dark"
      @selection-change="handleSelectionChange"
      style="width: 100%"
      size="mini"
      :height="tableHeight"
      border
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="学号" prop="chStuNo"></el-table-column>
      <el-table-column prop="chStuName" label="入住学生姓名"></el-table-column>
      <el-table-column prop="chBld" label="入住宿舍楼"></el-table-column>
      <el-table-column prop="chRoom" label="入住宿舍号"></el-table-column>
      <el-table-column prop="chBed" label="床位"></el-table-column>
      <el-table-column prop="chState" :formatter="stateFormat" label="当前状态"></el-table-column>
      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="details(scope.row.chStuNo)">详情</el-button>
          <el-button size="mini" type="success" @click="findOne(scope.row.chId)">编辑</el-button>
          <el-popconfirm
            style="margin-left: 10px;"
            title="您确定删除吗？"
            icon="el-icon-info"
            iconColor="red"
            @onConfirm="deleteOne(scope.row.chId)"
          >
            <el-button size="mini" type="danger" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件
          size-change: 当page-sizes 改变时触发事件
           @size-change="handleSizeChange"
          current-change：当页数发生变化时触发事件，
          @current-change="handleCurrentChange"
          current-page：当前是第几页
          page-size：页容量，也就是每页多少条数据
          total:总共有多少条数据,后台返回数据数值
    -->
    <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="stuTable.length"
    ></el-pagination>

    <!--    添加模态框-->
    <el-dialog title="添加入住学生" :visible.sync="addOneStuVisible" width="40%">
      <el-form
              class="addOneStu"
              ref="addOneStu"
              size="mini"
              style="text-align:center"
              :model="addOneStu"
              :inline="true"
              label-width="100px">
        <el-form-item label="姓名">
          <el-input v-model="addOneStu.chStuName" placeholder="请输入学生姓名"></el-input>
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="addOneStu.chStuNo" placeholder="请输入学生学号"></el-input>
        </el-form-item>
        <el-form-item label="宿舍楼">
          <el-input v-model="addOneStu.chBld" placeholder="请输入入住宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="房间号">
          <el-select v-model="addOneStu.chRoom" placeholder="请选择房间号">
            <el-option
                    v-for="item in room"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="床位">
          <el-select v-model="addOneStu.chBed" placeholder="请选择床位">
            <el-option
                    v-for="item in bed"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="当前状态" label-width="120px">
          <el-radio-group v-model="addOneStu.chState">
            <el-radio :label="0">入宿</el-radio>
            <el-radio :label="1">留宿</el-radio>
            <el-radio :label="2">调宿中</el-radio>
            <el-radio :label="3">退宿中</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addOneStuVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOneSubmit()">确 定</el-button>
      </span>
    </el-dialog>

    <!--  批量导入模态框  :before-close="handleClose"-->
    <el-dialog
      title="批量新增"
      :visible.sync="addAllVisible"
      width="30%"
      align="center"
    >
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
        <el-button @click="addAllVisible = false">取 消</el-button>
        <el-button type="primary" @click="upload" >确 定</el-button>
      </span>
    </el-dialog>

    <!--    详情模态框-->
    <el-dialog
            align="center"
            title="入住学生详情信息"
            :visible.sync="studentDetailsVisible"
            :data="stuDetails"
            width="50%">
      <el-form size="mini" style="text-align:center" :model="stuDetails" :inline="true" label-width="80px">
        <el-form-item label="学号">
          <el-input v-model="stuDetails.stuNo" disabled></el-input>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input v-model="stuDetails.stuName" disabled></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="stuDetails.stuSex" :formatter="stuFormat" disabled></el-input>
        </el-form-item>
        <el-form-item label="证件性质">
          <el-input v-model="stuDetails.stuPapersType" disabled></el-input>
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="stuDetails.stuGrade" disabled></el-input>
        </el-form-item>
        <el-form-item label="学历">
          <el-input v-model="stuDetails.stuEducation" disabled></el-input>
        </el-form-item>
        <el-form-item label="毕业时间">
          <el-input v-model="stuDetails.stuGraduation" disabled></el-input>
        </el-form-item>
        <el-form-item label="所属学院">
          <el-input v-model="stuDetails.stuCollege" disabled></el-input>
        </el-form-item>
        <el-form-item label="所属专业">
          <el-input v-model="stuDetails.stuMajor" disabled></el-input>
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="stuDetails.stuClass" disabled></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="studentDetailsVisible = false">返 回</el-button>
      </span>
    </el-dialog>

    <!--    修改模态框-->
    <el-dialog title="修改入住学生信息" :visible.sync="updateVisible" width="40%">
      <el-form
              class="updateCheckin"
              ref="updateCheckin"
              size="mini"
              style="text-align:center"
              :model="updateCheckin"
              :inline="true"
              label-width="100px">
        <el-form-item label="姓名">
          <el-input v-model="updateCheckin.chStuName" placeholder="请输入学生姓名"></el-input>
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="updateCheckin.chStuNo" placeholder="请输入学生学号"></el-input>
        </el-form-item>
        <el-form-item label="宿舍楼">
          <el-input v-model="updateCheckin.chBld" placeholder="请输入入住宿舍楼"></el-input>
        </el-form-item>
        <el-form-item label="房间号">
          <el-input v-model="updateCheckin.chRoom" placeholder="请选择房间号"></el-input>
        </el-form-item>
        <el-form-item label="床位">
          <el-input v-model="updateCheckin.chBed" placeholder="请选择床位"></el-input>
        </el-form-item>
        <el-form-item label="当前状态" label-width="120px">
          <el-radio-group v-model="updateCheckin.chState">
            <el-radio :label="0">入宿</el-radio>
            <el-radio :label="1">留宿</el-radio>
            <el-radio :label="2">调宿中</el-radio>
            <el-radio :label="3">退宿中</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateSubmit()">确 定</el-button>
      </span>
    </el-dialog>

  </el-main>
</template>

<script>
export default {
  methods: {

    roomNoList(){
      this.$axios({
        method: "get",
        url: "api/room/roomList"
      }).then((result) => {
        let roomList = result.data;
        for (let i=0;i<roomList.length;i++){
          let roomNo={
            value:roomList[i].roomNo,
            lable:roomList[i].roomNo,
          };
          this.room.push(roomNo);
        }
      })
    },
    // 模态框控制方法

    addOne() {
      this.addOneStuVisible = true;
    },
    addAll() {
      this.addAllVisible = true;
    },

    // 功能方法
    selectByLike(stuName) {
    this.$axios({
      method: "get",
      url: "api/checkin/selectByLike",
      params: {
        stuName: stuName,
      }
    }).then((result) =>{
      this.stuTable = result.data;
      console.log(this.stuTable);
    })
  },
    details(row){
      this.studentDetailsVisible = true;
      this.studentDetailsVisible = true;
      this.$axios({
        method: "get",
        url: "api/students/selectByStuNo",
        params: {
          stuNo: row,
        }
      }).then((result) =>{
        this.stuDetails = result.data;
        this.stuDetails.stuSex = result.data.stuSex == 0?'男':'女';
        this.stuDetails.stuPapersType = result.data.stuPapersType == 0?'居民身份证':'其他';
        if (result.data.stuEducation) {
          this.stuDetails.stuEducation = "大专";
        }else if(result.data.stuEducation){
          this.stuDetails.stuEducation = "本科";
        }else {
          this.stuDetails.stuEducation = "本科以上";
        }
        //console.log(this.stuDetails);
      })

    },
    stateFormat(row){
      switch (row.chState) {
        case 0:
          return '入宿';
        case 1:
          return '留宿';
        case 2:
          return '调宿';
        case 3:
          return '退宿';
      }
    },
    stuFormat(row){
      if(row.stuDetails.stuSex == 0){
        return "男"
      }else{
        return "女"
      }
    },
    getCheckinList: function(){
        this.$axios({
          method: "get",
          url: "api/checkin/checkinList"
        }).then((result) =>{
          this.stuTable = result.data;
          console.log(this.stuTable);
        })
    },

    addOneSubmit(){
      this.$refs.addOneStu.validate((valid) => {
        if(valid){
          var data = new FormData();
          data.append('chStuNo',this.addOneStu.chStuNo);
          data.append('chStuName',this.addOneStu.chStuName);
          data.append('chBld',this.addOneStu.chBld);
          data.append('chRoom',this.addOneStu.chRoom);
          data.append('chBed',this.addOneStu.chBed);
          data.append('chState',this.addOneStu.chState);
          this.$axios({
            method: "post",
            url: "api/checkin/insertOneStu",
            data: data
          }).then((result) => {
            if (result.data.code == 200) {
              this.$message({
                type: "success",
                duration: 1000,
                message: result.data.msg,
              });
              this.addOneStuVisible = false;
              this.$refs.addOneStu.resetFields(); // 刷新表单
              this.getCheckinList();
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
    deleteOne(chId){
      this.$axios({
        method: "get",
        url: "api/checkin/deleteOne",
        params: {
          chId: chId,
        }
      }).then((result) => {
        if (result.data.code == 200) {
          this.$message({
            type: "success",
            duration: 1000,
            message: result.data.msg,
          });
          this.getCheckinList();
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
      this.$confirm('是否删除所选信息?','提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(()=>{
        this.tableChecked.forEach(id =>{
          this.ids.push(id.chId);
        });
         console.log(this.ids);
        this.$axios({
          method: "delete",
          url: "api/checkin/delete",
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
            this.getCheckinList();
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
    findOne(chId){
      this.updateVisible = true;
       console.log(chId)
      this.$axios({
        method: "get",
        url: "api/checkin/findOne",
        params: {
          chId: chId,
        }
      }).then((result) =>{
        this.updateCheckin = result.data;
        //console.log(this.updateCheckin);
      })
    },
    updateSubmit(){
      this.$refs.updateCheckin.validate((valid) => {
        if(valid){
          var data = new FormData();
          data.append('chId',this.updateCheckin.chId)
          data.append('chStuNo',this.updateCheckin.chStuNo);
          data.append('chStuName',this.updateCheckin.chStuName);
          data.append('chBld',this.updateCheckin.chBld);
          data.append('chRoom',this.updateCheckin.chRoom);
          data.append('chBed',this.updateCheckin.chBed);
          data.append('chState',this.updateCheckin.chState);
          this.$axios({
            method: "post",
            url: "api/checkin/updateOneStu",
            data: data
          }).then((result) => {
            if (result.data.code == 200) {
              this.$message({
                type: "success",
                duration: 1000,
                message: result.data.msg,
              });
              this.updateVisible = false;
              this.getCheckinList();
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
    modeUpload: function(item) {
      // console.log(item.file);
      this.mode = item.file
        console.log(item.file);
    },
    upload() {
      const data = new FormData();
       data.append('file', this.mode);
      this.$axios({
        method: "post",
        url: "api/checkin/upload",
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
          this.addAllVisible = false;
          this.getCheckinList();
        }
      });
    },
    dow(){
      this.$axios({
        url: "api/checkin/download",
        method: 'post',
        responseType: 'arraybuffer'
      }).then((res) => {
        let url = window.URL.createObjectURL(new Blob([res.data]))
        let a = document.createElement('a')
        a.setAttribute("download","CheckTest.xlsx")
        a.href = url;
        a.click();
      })

      },

    //pageSize改变调用
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    // 页数改变调用
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage=val;
    },
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
    },
  },
  data() {
    return {
      pageSize: 10,
      currentPage: 1,
      mode: {},
      addAllVisible: false,
      addOneStuVisible: false,
      studentDetailsVisible: false,
      updateVisible: false,
      addOneStu: {},
      updateCheckin: {},
      stuDetails: [],
      tableHeight: 0,
      stuFrom: {
        stuName: "",
      },
      stuTable: [],
      tableChecked: [],//选中显示的值
      ids:[],//批量删除id
      room: [
        // {
        //   value: "101",
        //   lable: "101"
        // },{
        //   value: "102",
        //   lable: "102"
        // }
      ],
      bed: [
        {
          value: "1",
          label: "1号床位"
        },
        {
          value: "2",
          label: "2号床位"
        },
        {
          value: "3",
          label: "3号床位"
        },
        {
          value: "4",
          label: "4号床位"
        },
        {
          value: "5",
          label: "5号床位"
        },
        {
          value: "6",
          label: "6号床位"
        }
      ]
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 280;
    });
  },
  created: function () {
    this.getCheckinList();
    this.roomNoList();
  },
  };
</script>

<style>
</style>