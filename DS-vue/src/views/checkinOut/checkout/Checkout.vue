<template>
  <el-main>
    <!--搜索框-->
    <el-form size="mini" :model="CheckoutForm" label-width="80px">
      <el-row>
        <el-col :span="5">
          <el-form-item label="客户名:">
            <el-select v-model="CheckoutForm.cusId" clearable placeholder="请选择">
              <el-option
                      v-for="item in cusNames"
                      :key="item.id"
                      :label="item.cusName"
                      :value="item.id">
              </el-option>
            </el-select>
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
            :data="checkoutData"
            size="mini"
            :height="tableHeight"
            @selection-change="handleSelectionChange"
            border
            style="width: 100%"
            tooltip-effect="dark"
    >
      <el-table-column align="center" type="selection"></el-table-column>
      <el-table-column align="center" prop="customer.cusName" label="住宿人姓名"></el-table-column>
      <el-table-column align="center" prop="bed.floor" label="宿舍楼层"></el-table-column>
      <el-table-column align="center" prop="bed.roomNo" label="宿舍号"></el-table-column>
      <el-table-column align="center" prop="bed.bedNo" label="床位号"></el-table-column>
      <el-table-column align="center" prop="checkinDate" label="入住日期" show-overflow-tooltip></el-table-column>
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

    <el-dialog title="新增宿舍" :visible.sync="addOneRoomVisible" width="30%">
      <el-form
              ref="addOneForm"
              size="mini"
              style="text-align:center"
              :model="addOneForm"
              :inline="true"
              label-width="100px"
      >
        <el-form-item label="入住人：">
          <el-select v-model="addOneForm.cusId" clearable placeholder="请选择">
            <el-option
                    v-for="item in cusNames"
                    :key="item.id"
                    :label="item.cusName"
                    :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="房间号码：">
          <el-select v-model="addOneForm.roomNo" clearable placeholder="请选择">
            <el-option
                    v-for="item in options"
                    :key="item.roomNo"
                    :label="item.roomNo"
                    :value="item.roomNo">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="床位号：">
          <el-select @click.native="selBedNo" v-model="addOneForm.bedId" clearable placeholder="请选择">
            <el-option
                    v-for="item in bedNos"
                    :key="item.id"
                    :label="item.bedNo"
                    :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addOneRoomVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOneRoom">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改宿舍" :visible.sync="updateOneRoomVisible" width="40%">
      <el-form
              ref="addOne"
              size="mini"
              style="text-align:center"
              :model="updateOneForm"
              :inline="true"
              label-width="100px"
      >
        <el-form-item label="入住人：">
          <el-select v-model="updateOneForm.cusId" clearable placeholder="请选择">
            <el-option
                    v-for="item in cusNames"
                    :key="item.id"
                    :label="item.cusName"
                    :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="房间号码：">
          <el-select v-model="updateOneForm.roomNo" clearable placeholder="请选择">
            <el-option
                    v-for="item in options"
                    :key="item.roomNo"
                    :label="item.roomNo"
                    :value="item.roomNo">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="床位号：">
          <el-select @click.native="upselBedNo" v-model="updateOneForm.bedNo" clearable placeholder="请选择">
            <el-option
                    v-for="item in bedNos"
                    :key="item.id"
                    :label="item.bedNo"
                    :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="入住日期：">
          <el-date-picker
                  v-model="updateOneForm.checkinDate"
                  type="date"
                  placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateOneRoomVisible = false">取 消</el-button>
        <el-button @click="setOne" type="primary">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="宿舍详情" :visible.sync="roomVisible" width="30%">
      <el-form
              size="mini"
              style="text-align:center"
              :model="checkin"
              :inline="true"
              label-width="100px"
              disabled
      >
        <el-form-item label="客户名：">
          <el-input v-model="checkin.customer.cusName" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="性别：">
          <el-radio-group v-model="checkin.customer.cusSex">
            <el-radio :label="0">男</el-radio>
            <el-radio :label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄：">
          <el-input v-model="checkin.customer.cusAge" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="所属楼房：">
          <el-input v-model="checkin.bed.floor" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="房间号码：">
          <el-input v-model="checkin.bed.roomNo" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="床位号：">
          <el-input v-model="checkin.bed.bedNo" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="入住日期：">
          <el-input v-model="checkin.checkinDate" placeholder=""></el-input>
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
    name: "Checkout",
    methods: {
      created(){

      }
    },
    data(){
      return{
        CheckoutForm: {
          cusName: ""
        },
        checkoutData: []
      };
    }
  }
</script>

<style scoped>

</style>