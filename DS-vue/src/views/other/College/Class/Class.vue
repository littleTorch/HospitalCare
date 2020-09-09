<template>
    <el-main>
    <!--搜索框-->
    <el-form size="mini" :model="classFrom" label-width="80px">
      <el-row>
        <el-col :span="5">
          <el-form-item label="班级:">
            <el-input v-model="classFrom.className" placeholder="请输入班级名称或年级"></el-input>
          </el-form-item>
        </el-col>
        <el-button @click="selectbyName(classFrom.className)" style="margin-left: 20px;" class="btn-left" size="mini" type="primary" icon="el-icon-search">查询</el-button>
        <el-button @click="addOneClass" size="mini" type="primary" icon="el-icon-plus">新增班级</el-button>
        <el-button @click="deleteList(tableChecked)" size="mini" type="primary" icon="el-icon-delete">批量删除</el-button>
      </el-row>
    </el-form>
    <!--数据表格-->
    <el-table
            :data="classData.slice((currentPage-1) * pageSize ,currentPage * pageSize)"
            size='mini'
            :height="tableHeight"
            @selection-change="handleSelectionChange"
            border
            style="width: 100%">
      <el-table-column  align="center" type="selection"></el-table-column>
      <el-table-column align="center" prop="classMajor" label="所属专业" :formatter="majorFormat"></el-table-column>
      <el-table-column align="center" prop="className" label="班级名称"></el-table-column>
      <el-table-column align="center" prop="classNum" label="班级人数" ></el-table-column>
      <el-table-column label="操作" width="350" align="center">
        <template slot-scope="scope">
          <el-button @click="updateClass(scope.row)" type="success" size="mini">编辑</el-button>
            <el-popconfirm
                    style="margin-left: 10px;"
                    title="您确定删除该班级吗？"
                    icon="el-icon-info"
                    iconColor="red"
                    @onConfirm="deleteOne(scope.row.classId)">
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
      :total="classData.length"
    ></el-pagination>

<!--    新增班级    -->
        <el-dialog title="新增班级" :visible.sync="addOneVisible" width="30%">
            <el-form
                    :rules="rules"
                    align="center"
                    class="addOne"
                    ref="addOne"
                    size="mini"
                    style="text-align:center"
                    :model="addOne"
                    :inline="true"
                    label-width="100px">
                <el-form-item label="所属专业">
                    <el-select v-model="addOne.classMajor" placeholder="请选择所属专业">
                        <el-option
                                v-for="item in major"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="班级名称">
                    <el-input v-model="addOne.className" placeholder="请输入新班级名称"></el-input>
                </el-form-item>
                <el-form-item label="班级人数">
                    <el-input v-model="addOne.classNum" placeholder="请输入新班级人数"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
            <el-button @click="addOneVisible = false">取 消</el-button>
            <el-button @click="addSubmit()" type="primary" >确 定</el-button>
        </span>
        </el-dialog>
<!--    修改班级    -->
        <el-dialog title="修改班级信息" :visible.sync="updateVisible" width="30%">
            <el-form
                    align="center"
                    class="updateData"
                    ref="updateData"
                    size="mini"
                    style="text-align:center"
                    :model="updateData"
                    :inline="true"
                    label-width="100px"
                    :rules="rules">
                <el-form-item label="所属专业">
                    <el-select v-model="value.value" placeholder="请选择所属专业">
                        <el-option
                                v-for="item in major"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="班级名称">
                    <el-input v-model="updateData.className" placeholder="请输入新班级名称"></el-input>
                </el-form-item>
                <el-form-item label="班级人数">
                    <el-input v-model="updateData.classNum" placeholder="请输入新班级人数"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
            <el-button @click="updateVisible = false">取 消</el-button>
            <el-button @click="updateSubmit()" type="primary" >确 定</el-button>
        </span>
        </el-dialog>

    </el-main>
</template>

<script>

export default {
    created:function(){
        this.getClassList();
        this.getMajorList();
    },
  methods: {
      addOneClass(){
          this.addOneVisible = true;
      },
      updateClass(row){
         let major= row.classMajor;
         let majorData = this.majorData;
          for (let i = 0; i <majorData.length; i++) {
              console.log(majorData[i].majorNo);
              if (majorData[i].majorNo==major){
                  this.value.label=majorData[i].majorName;
                  this.value.value=majorData[i].majorNo;
              }
          }
        this.updateData = row;
        this.updateVisible = true;
      },

      getClassList(){
          this.$axios({
              method: "get",
              url: "api/class/classList"
          }).then((result) =>{
              this.classData = result.data;
              console.log(this.classData);
          })
      },
      getMajorList(){
          this.$axios({
              method: "get",
              url: "api/major/majorList"
          }).then((result) =>{
              this.majorData = result.data;
              console.log(this.majorData);
              for(let i=0;i<this.majorData.length;i++){
                  let majorId ={
                      value:this.majorData[i].majorNo,
                      label:this.majorData[i].majorName,
                  }
                  this.major.push(majorId);
              }
          })
      },
      selectbyName(name){
          if(name ==""){
              this.getClassList();
          }
          this.$axios({
              methods: "get",
              url: "api/class/selectByName",
              params:{
                  className: name,
              }
          }).then((result)=>{
              this.classData = result.data;
          })
      },


      addSubmit(){
          this.$refs.addOne.validate((valid)=>{
              if(valid){
                  var data = new FormData();
                  data.append('className',this.addOne.className);
                  data.append('classMajor',this.addOne.classMajor);
                  data.append('classNum',this.addOne.classNum);
                  this.$axios({
                      method: "post",
                      url: "api/class/insertOne",
                      data: data,
                  }).then((result)=>{
                      if(result.data.code ==200){
                          this.$message({
                              type: "success",
                              duration: 1000,
                              message: result.data.msg,
                          });
                          this.addOneVisible = false;
                          this.$refs.addOne.resetFields();
                          this.getClassList();
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
      },

      updateSubmit(){
          this.$refs.updateData.validate((valid)=>{
              if(valid){
                  var data = new FormData();
                  data.append('classId',this.updateData.classId);
                  data.append('classMajor',this.value.value);
                  data.append('className',this.updateData.className);
                  data.append('classNum',this.updateData.classNum);
                  this.$axios({
                      method: "post",
                      url: "api/class/update",
                      data: data,
                  }).then((result)=>{
                      if (result.data.code ==200){
                          this.$message({
                              type: "success",
                              duration: 1000,
                              message: result.data.msg,
                          });
                          this.updateVisible = false;
                          this.getClassList();
                      } else {
                          this.$message({
                              type: "error",
                              duration: 1000,
                              message: result.data.msg,
                          });
                      }
                  })
              }
          })
      },

      deleteOne(id){
          this.$axios({
              method: "get",
              url: "api/class/deleteOne",
              params: {
                  classId: id,
              }
          }).then((result)=>{
              if (result.data.code==200){
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
              alert("您未选择任一班级");
          }else {
              this.$confirm('是否删除所选班级信息?', '提示', {
                  confirmButtonText: '确定',
                  cancelButtonText: '取消',
                  type: 'warning',
              }).then(() => {
                  this.tableChecked.forEach(id => {
                      this.ids.push(id.classId);
                  });
                  this.$axios({
                      method: "delete",
                      url: "api/class/delete",
                      headers: {
                          'Content-Type': 'application/json;charset=UTF-8'
                      },
                      data: JSON.stringify(this.ids),
                  }).then((result) => {
                      if (result.data.code == 200){
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
      majorFormat(row){
          for(let i=0;i<this.majorData.length;i++){
              if(row.classMajor == this.majorData[i].majorNo){
                  return this.majorData[i].majorName;
              }
          }
      },
      handleSelectionChange(val){
          this.tableChecked = val;
      },
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 280;
    });
  },
  data() {
    return {
        rules:{
            classMajor: [
                { required: true, message: "所属专业不能为空", trigger: "blur" },
            ],
            className: [
                { required: true, message: "班级名称不能为空", trigger: "blur" },
            ],
            classNum:[
                { required: true, message: "班级人数不能为空", trigger: "blur" },
            ]
        },
        addOneVisible: false,
        updateVisible: false,
        pageSize:10,
        currentPage:1,
        ids:[],
        tableChecked:[],
        addOne:[],
        //搜索框数据绑定
        classFrom: [],
        updateOneForm: [],
         // 表格高度
        tableHeight: window.innerHeight,
        // 表格数据
        classData: [],
        majorData:[],
        major: [],
        updateData: [],
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