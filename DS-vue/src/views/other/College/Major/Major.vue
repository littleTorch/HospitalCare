<template>
    <el-main>
    <!--搜索框-->
    <el-form size="mini" :model="majorFrom" label-width="80px">
      <el-row>
        <el-col :span="5">
          <el-form-item label="院系编号:">
            <el-input v-model="majorFrom.majorCollege" placeholder="请输入院系编号"></el-input>
          </el-form-item>
        </el-col>
        <el-button @click="selectByNo(majorFrom.majorCollege)" style="margin-left: 20px;" class="btn-left" size="mini" type="primary" icon="el-icon-search">查询</el-button>
        <el-button @click="addOneMajor" size="mini" type="primary" icon="el-icon-plus">新增专业</el-button>
        <el-button @click="dow" size="mini" type="primary" icon="el-icon-download">批量导入模板下载</el-button>
        <el-button @click="addManyMajor" size="mini" type="primary" icon="el-icon-plus">批量申请</el-button>
        <el-button @click="deleteList(tableChecked)" size="mini" type="primary" icon="el-icon-delete">批量删除</el-button>
        
      </el-row>
    </el-form>
    <!--数据表格-->
    <el-table
            :data="majorData.slice((currentPage-1) * pageSize ,currentPage * pageSize)"
            size='mini'
            :height="tableHeight"
            @selection-change="handleSelectionChange"
            border
            style="width: 100%">
      <el-table-column  align="center" type="selection"></el-table-column>
      <el-table-column align="center" prop="majorNo" label="专业编号"></el-table-column>
      <el-table-column align="center" prop="majorName" label="专业名称"></el-table-column>
      <el-table-column align="center" prop="majorCollege" label="所属院系" :formatter="collegeFormat"></el-table-column>
      <el-table-column align="center" prop="majorState" label="当前使用状态" :formatter="stateFormat"></el-table-column>
      <el-table-column label="操作" width="400" align="center">
        <template slot-scope="scope">
          <el-button @click="updateState(scope.row.majorId)" type="primary" size="mini">设置使用状态</el-button>
          <el-button @click="updateMajor(scope.row)" type="success" size="mini">编辑</el-button>
          <el-popconfirm
                  style="margin-left: 10px;"
                  title="您确定删除该院系吗？"
                  icon="el-icon-info"
                  iconColor="red"
                  @onConfirm="deleteOne(scope.row.majorId)">
              <el-button size="mini" type="danger" slot="reference">删除</el-button>
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
      :total="majorData.length"
    ></el-pagination>

<!--        新增专业-->
    <el-dialog title="新增专业" :visible.sync="addOneVisible" width="30%">
        <el-form
                align="center"
                class="addOne"
                ref="addOne"
                size="mini"
                style="text-align:center"
                :model="addOne"
                :inline="true"
                label-width="100px">
            <el-form-item label="专业编号">
                <el-input v-model="addOne.majorNo" placeholder="请输入新院系编号"></el-input>
            </el-form-item>
            <el-form-item label="专业名称">
                <el-input v-model="addOne.majorName" placeholder="请输入新院系名称"></el-input>
            </el-form-item>
            <el-form-item label="所属院系">
                <el-select v-model="addOne.majorCollege" placeholder="请选择所属校区">
                    <el-option
                            v-for="item in college"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="addOneVisible = false">取 消</el-button>
            <el-button @click="addSubmit()" type="primary" >确 定</el-button>
        </span>
    </el-dialog>
        <!--        修改专业信息-->
    <el-dialog title="新增专业" :visible.sync="updateVisible" width="30%">
            <el-form
                    align="center"
                    class="updateData"
                    ref="updateData"
                    size="mini"
                    style="text-align:center"
                    :model="updateData"
                    :inline="true"
                    label-width="100px">
                <el-form-item label="专业编号">
                    <el-input v-model="updateData.majorNo" placeholder="请输入院系编号"></el-input>
                </el-form-item>
                <el-form-item label="专业名称">
                    <el-input v-model="updateData.majorName" placeholder="请输入院系名称"></el-input>
                </el-form-item>
                <el-form-item label="所属院系">
                    <el-select v-model="value.value" placeholder="请选择所属校区">
                        <el-option
                                v-for="item in college"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="updateVisible = false">取 消</el-button>
                <el-button @click="updateSubmit()" type="primary" >确 定</el-button>
            </span>
        </el-dialog>
<!--使用状态-->
    <el-dialog title="设置使用状态" :visible.sync="stateVisible" width="20%">
        <el-form
                class="stateData"
                ref="stateData"
                size="mini"
                style="text-align:center"
                :model="stateData"
                :inline="true"
                label-width="100px">
            <el-form-item label="当前使用状态" label-width="120px">
                <el-radio-group v-model="stateData.majorState">
                    <el-radio :label="0">停办</el-radio>
                    <el-radio :label="1">开办</el-radio>
                </el-radio-group>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="stateVisible = false">取 消</el-button>
            <el-button @click="stateSubmit(stateData)" type="primary" >确 定</el-button>
        </span>
    </el-dialog>

 <!--批量-->
    <el-dialog
                title="批量添加专业"
                :visible.sync="addManyVisible"
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
                <el-button @click="addManyVisible = false">取 消</el-button>
                <el-button type="primary" @click="upload">确 定</el-button>
            </span>
        </el-dialog>

  </el-main>
</template>

<script>

export default {
    created:function(){
        this.getMajorList();
        this.getColData();
    },
  methods: {
      addOneMajor(){
          this.addOneVisible = true;
      },
      addManyMajor(){
          this.addManyVisible = true;
      },
      updateMajor(row){
      //console.log(row);
        let college = row.majorCollege;
        let colData = this.colData;
        for(let i = 0; i <colData.length; i++){
            if(colData[i].collegeNo == college){
                this.value.label=colData[i].collegeName;
                this.value.value=colData[i].collegeNo;
            }
        }
        this.updateData = row;
        this.updateVisible = true;
      },
      updateState(id){
          this.stateData.majorId = id;
          this.stateVisible = true;
      },

      getColData(){
          this.$axios({
              method: "get",
              url: "api/colleges/collegesList"
          }).then((result) =>{
              this.colData = result.data;
              console.log(this.colData);
              for(let i=0;i<this.colData.length;i++){
                  let colId ={
                      value:this.colData[i].collegeNo,
                      label:this.colData[i].collegeName,
                  }
                  this.college.push(colId);
              }
          })
      },
      getMajorList(){
          this.$axios({
              method: "get",
              url: "api/major/majorList"
          }).then((result) =>{
              this.majorData = result.data;
              console.log(this.majorData);
          })
      },
      selectByNo(majorCollege){
          if(majorCollege==""){
              this.getMajorList();
          }
          this.$axios({
              methods: "get",
              url: "api/major/selectByCol",
              params:{
                  majorCollege: majorCollege,
              }
          }).then((result)=>{
              this.majorData = result.data;
          })
      },

      addSubmit(){
          this.$refs.addOne.validate((valid)=>{
              if(valid){
                  var data = new FormData();
                  data.append('majorNo',this.addOne.majorNo);
                  data.append('majorName',this.addOne.majorName);
                  data.append('majorCollege',this.addOne.majorCollege);
                  this.$axios({
                      method: "post",
                      url: "api/major/insert",
                      data: data,
                  }).then(()=>{
                      alert("添加成功");
                      this.addOneVisible = false;
                      this.$refs.addOne.resetFields();
                      this.getMajorList();
                  })
              }
          })
      },

      updateSubmit(){
          this.$refs.updateData.validate((valid)=>{
              if(valid){
                  var data = new FormData();
                  data.append('majorId',this.updateData.majorId);
                  data.append('majorNo',this.updateData.majorNo);
                  data.append('majorName',this.updateData.majorName);
                  data.append('majorCollege',this.updateData.majorCollege);
                  this.$axios({
                      method: "post",
                      url: "api/major/update",
                      data: data,
                  }).then(()=>{
                      alert("修改操作成功！")
                      this.updateVisible = false;
                      this.getMajorList();
                  })
              }
          })
      },
      stateSubmit(stateData){
          var data = new FormData();
          data.append('majorId',stateData.majorId);
          data.append('majorState',stateData.majorState);
          this.$axios({
              method: "post",
              url: "api/major/update",
              data: data,
          }).then(()=>{
              alert("修改操作成功！")
              this.stateVisible = false;
              this.getMajorList();
          })
      },

      deleteOne(id){
          this.$axios({
              method: "get",
              url: "api/major/deleteOne",
              params: {
                  majorId: id,
              }
          }).then(()=>{
              alert("已删除该专业记录！");
              location.reload();
          })
      },
      deleteList(){
          if(this.tableChecked.length ===0){
              alert("您未选择任一专业");
          }else {
              this.$confirm('是否删除所选专业信息?', '提示', {
                  confirmButtonText: '确定',
                  cancelButtonText: '取消',
                  type: 'warning',
              }).then(() => {
                  this.tableChecked.forEach(id => {
                      this.ids.push(id.majorId);
                  });
                  this.$axios({
                      method: "delete",
                      url: "api/major/delete",
                      headers: {
                          'Content-Type': 'application/json;charset=UTF-8'
                      },
                      data: JSON.stringify(this.ids),
                  }).then(() => {
                      alert("批量删除成功！");
                      location.reload();
                  })
              })
          }
      },
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
              url: "api/major/upload",
              data: data,
              headers: {
                  'Content-Type': 'multipart/form-data'
              }
          }).then((result) =>{
              console.log(result.data);
              if(result.data == "ok"){
                  this.addManyVisible = false;
                  this.getMajorList();
              }else{
                  alert("上传失败！");
                  this.addManyVisible = false;
              }
          })
      },
      dow(){
          this.$axios({
              url: "api/major/download",
              method: 'post',
              responseType: 'arraybuffer'
          }).then((res) => {
              let url = window.URL.createObjectURL(new Blob([res.data]))
              let a = document.createElement('a')
              a.setAttribute("download","Major.xlsx")
              a.href = url
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
      this.currentPage = val;
    },
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
    },
      //选项框的值
      handleSelectionChange(val){
        this.tableChecked = val;
      },
      stateFormat(row){
          if(row.majorState == 0){
              return "暂未开办";
          }else{
              return "已开办";
          }
      },
      collegeFormat(row){
          for(let i=0;i<this.colData.length;i++){
              if(row.majorCollege == this.colData[i].collegeNo){
                  return this.colData[i].collegeName;
              }
          }
      }
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 280;
    });
  },
  data() {
    return {
        action: 'https://jsonplaceholder.typicode.com/posts/',
        pageSize:5,
        currentPage:1,
        updateVisible: false,
        addOneVisible: false,
        stateVisible: false,
        addManyVisible: false,
        addOne: {},
        //搜索框数据绑定
        majorFrom: [],
        updateOneForm: [],
         // 表格高度
        tableHeight: window.innerHeight,
         // 表格数据
        majorData: [],
        tableChecked:[],
        colData: [],
        college: [],
        updateData:[],
        stateData:[],
        ids: [],
        mode:{},
        value: {
            value:'',
            label:'',
        },
    };
  },
};
</script>

<style>
</style>