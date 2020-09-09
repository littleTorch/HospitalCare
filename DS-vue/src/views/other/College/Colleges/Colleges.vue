<template>
    <el-main>
    <!--搜索框-->
    <el-form size="mini" label-width="80px" :model="colFrom">
      <el-row>
        <el-col :span="5">
          <el-form-item label="学院编号:">
            <el-input v-model="colFrom.collegeNo" placeholder="请输入学院编号"></el-input>
          </el-form-item>
        </el-col>
        <el-button @click="selectByNo(colFrom.collegeNo)" style="margin-left: 20px;" class="btn-left" size="mini" type="primary" icon="el-icon-search">查询</el-button>
        <el-button @click="addOneCol" size="mini" type="primary" icon="el-icon-plus">新增院系</el-button>
        <el-button @click="deleteList(tableChecked)" size="mini" type="primary" icon="el-icon-delete">批量删除</el-button>

      </el-row>
    </el-form>
    <!--数据表格-->
    <el-table
            :data="colData.slice((currentPage-1) * pageSize ,currentPage * pageSize)"
            size='mini'
            :height="tableHeight"
            @selection-change="handleSelectionChange"
            border
            style="width: 100%">
      <el-table-column  align="center" type="selection"></el-table-column>
      <el-table-column align="center" prop="collegeNo" label="学院编号"></el-table-column>
      <el-table-column align="center" prop="collegeName" label="学院名称"></el-table-column>
      <el-table-column align="center" prop="collegeSchool" :formatter="schoolFormat" label="所在校区" show-overflow-tooltip></el-table-column>
      <el-table-column label="操作" width="400" align="center">
        <template slot-scope="scope">
          <el-button @click="details(scope.row.collegeNo)" type="primary" size="mini">所属专业</el-button>
          <el-button @click="updateSchool(scope.row.collegeId)" type="primary" size="mini">设置所属</el-button>
          <el-button @click="updateCol(scope.row)" type="success" size="mini">编辑</el-button>
          <el-popconfirm
                    style="margin-left: 10px;"
                    title="您确定删除该院系吗？"
                    icon="el-icon-info"
                    iconColor="red"
                    @onConfirm="deleteOne(scope.row.collegeId)">
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
      :total="colData.length"
    ></el-pagination>

<!--        所属专业-->
        <el-dialog title="所属专业" :visible.sync="majorVisiable" width="30%">
            <el-table
                :data="majorData"
                size="mini"
                style="width: 100%"
                tooltip-effect="dark">
                <el-table-column align="center" property="majorNo" label="专业编号"></el-table-column>
                <el-table-column align="center" property="majorName" label="专业名称"></el-table-column>
                <el-table-column align="center" property="majorState" :formatter="stateFormat" label="当前使用状态"></el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="majorVisiable = false">返 回</el-button>
            </span>
        </el-dialog>

<!--        新增院系-->
    <el-dialog title="新增院系" :visible.sync="addOneColVisible" width="30%">
       <el-form
               align="center"
               class="addOne"
               ref="addOne"
               size="mini"
               style="text-align:center"
               :model="addOne"
               :inline="true"
               label-width="100px">
        <el-form-item label="院系编号">
          <el-input v-model="addOne.collegeNo" placeholder="请输入新院系编号"></el-input>
        </el-form-item>
        <el-form-item label="学院名称">
          <el-input v-model="addOne.collegeName" placeholder="请输入新院系名称"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addOneColVisible = false">取 消</el-button>
        <el-button @click="addSubmit()" type="primary" >确 定</el-button>
      </span>
    </el-dialog>

<!--        设置所属-->
        <el-dialog title="设置所属校区" :visible.sync="schoolVisiable" width="30%">
            <el-form
                align="center"
                :model="updateSch"
                size="mini"
                :inline="true"
                label-width="100px">
                <el-form-item label="所属校区" prop="collegeSchool">
                    <el-select v-model="updateSch.collegeSchool" placeholder="请选择所属校区">
                        <el-option
                                v-for="item in school"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="schoolVisiable = false">取 消</el-button>
                <el-button @click="updateSchoolSubmit(row)" type="primary" >确 定</el-button>
            </span>
        </el-dialog>

<!--    修改-->
    <el-dialog title="修改院系信息" :visible.sync="updateOneColVisible" width="30%">
       <el-form
               class="updateData"
               ref="updateData"
               size="mini"
               style="text-align:center"
               :model="updateData"
               :inline="true"
               label-width="100px">
        <el-form-item label="学院编号">
          <el-input v-model="updateData.collegeNo" placeholder="请输入学院编号"></el-input>
        </el-form-item>
        <el-form-item label="学院名称">
          <el-input v-model="updateData.collegeName" placeholder="请输入学院名称"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateOneColVisible = false">取 消</el-button>
        <el-button @click="updateSubmit()" type="primary" >确 定</el-button>
      </span>
    </el-dialog>


  </el-main>
</template>

<script>

export default {
    created:function(){
        this.getSchoolList();
        this.getColData();
    },
  methods: {
      details(id){
          this.getMajorList(id);
          this.majorVisiable = true;
      },
    addOneCol() {
       this.addOneColVisible = true;
    },
      updateSchool(id){
          this.updateSch.collegeId = id;
          this.schoolVisiable = true;
      },
    updateCol(row){
      this.updateData = row;
      this.updateOneColVisible = true;
    },

    getColData(){
        this.$axios({
            method: "get",
            url: "api/colleges/collegesList"
        }).then((result) =>{
            this.colData = result.data;
            console.log(this.colData);
        })
    },
      getSchoolList(){
          this.$axios({
              methods: "get",
              url: "api/school/schoolList",
          }).then((result)=>{
              this.schoolData = result.data;
              console.log(this.schoolData);
              for (let i=0;i<this.schoolData.length;i++){
                  let schId={
                      value:this.schoolData[i].schId,
                      label:this.schoolData[i].schName,
                  };
                  this.school.push(schId);
              }
          })
      },
      getMajorList(id){
          this.$axios({
              methods: "get",
              url: "api/major/selectByCol",
              params:{
                  majorCollege: id,
              }
          }).then((result)=>{
              this.majorData = result.data;
          })
      },
      selectByNo(no){
          if(no==""){
              this.getColData();
          }
          this.$axios({
              methods: "get",
              url: "api/colleges/selectByNo",
              params:{
                  collegeNo: no,
              }
          }).then((result)=>{
              this.colData = result.data;
          })
      },

      addSubmit(){
          this.$refs.addOne.validate((valid)=>{
              if(valid){
                  var data = new FormData();
                  data.append('collegeNo',this.addOne.collegeNo);
                  data.append('collegeName',this.addOne.collegeName);
                  this.$axios({
                      method: "post",
                      url: "api/colleges/insertOne",
                      data: data,
                  }).then(()=>{
                      alert("添加成功");
                      this.addOneColVisible = false;
                      this.$refs.addOne.resetFields();
                      this.getColData();
                  })
              }
          })
      },

      updateSchoolSubmit(){
          var data = new FormData();
          data.append('collegeId',this.updateSch.collegeId)
          data.append('collegeSchool',this.updateSch.collegeSchool);
          this.$axios({
              method: "post",
              url: "api/colleges/update",
              data: data
          }).then(()=>{
              this.schoolVisiable = false;
              this.getColData();
          })
      },
      updateSubmit(){
          this.$refs.updateData.validate((valid)=>{
              if(valid){
                  var data = new FormData();
                  data.append('collegeId',this.updateData.collegeId);
                  data.append('collegeName',this.updateData.collegeName);
                  data.append('collegeNo',this.updateData.collegeNo);
                  this.$axios({
                      method: "post",
                      url: "api/colleges/update",
                      data: data,
                  }).then(()=>{
                      alert("修改操作成功！")
                      this.updateOneColVisible = false;
                      this.getColData();
                  })
              }
          })
      },

      deleteOne(id){
          this.$axios({
              method: "get",
              url: "api/colleges/deleteOne",
              params: {
                  collegeId: id,
              }
          }).then(()=>{
              alert("已删除该院系记录！");
              location.reload();
          })
      },
      deleteList(){
          if(this.tableChecked.length ===0){
              alert("您未选择任一院系");
          }else {
              this.$confirm('是否删除所选院系信息?', '提示', {
                  confirmButtonText: '确定',
                  cancelButtonText: '取消',
                  type: 'warning',
              }).then(() => {
                  this.tableChecked.forEach(id => {
                      this.ids.push(id.collegeId);
                  });
                  this.$axios({
                      method: "delete",
                      url: "api/colleges/delete",
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

      //获取选项框的值
      handleSelectionChange(val){
          this.tableChecked = val;
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
      schoolFormat(row){
        for(var i=0;i<this.schoolData.length;i++){
            if(this.schoolData[i].schId == row.collegeSchool){
                return this.schoolData[i].schName;
            }else if(row.collegeSchool ==0){
                return "暂无校区";
            }
        }
      },
      stateFormat(row){
          if(row.majorState ==1){
              return "已开办"
          }else{
              return "暂未开办专业"
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
        pageSize:10,
        currentPage:1,
      addOneColVisible: false,
      updateOneColVisible: false,
        majorVisiable: false,
        schoolVisiable: false,
      addOne: [],
      // 表格高度
      tableHeight: window.innerHeight,
      // 表格数据
      colData: [],
        tableChecked:[],
        ids:[],
        schoolData: [],
        majorData: [],
        updateSch: [],
        school:[],
        updateData: [],
        colFrom:[],
    };
  },
};
</script>

<style>
</style>