<template>
  <el-main>
    <!--搜索框-->
    <el-form size="mini" :model="CheckoutForm" label-width="80px">
      <el-row>
        <el-col :span="5">
          <el-form-item label="客户名:">
            <!--<el-select v-model="CheckoutForm.cusId" clearable placeholder="请选择">
              <el-option
                      v-for="item in cusNames"
                      :key="item.id"
                      :label="item.cusName"
                      :value="item.id">
              </el-option>
            </el-select>-->
          </el-form-item>
        </el-col>
        <el-button
                style="margin-left: 20px;"
                class="btn-left"
                size="mini"
                type="primary"
                icon="el-icon-search"
        >查询
        </el-button>
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
      <el-table-column align="center" prop="customer.cusName" label="退住人姓名"></el-table-column>
      <el-table-column align="center" prop="bed.floor" label="宿舍楼层"></el-table-column>
      <el-table-column align="center" prop="bed.roomNo" label="宿舍号"></el-table-column>
      <el-table-column align="center" prop="bed.bedNo" label="床位号"></el-table-column>
      <el-table-column align="center" prop="checkoutDate" label="退住日期" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="customer.checkoutStatus" :formatter="checkoutStatusF" label="退住状态" ></el-table-column>
      <el-table-column label="操作" width="250" align="center">
        <!--<template slot-scope="scope">
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
        </template>-->
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

    <!--<el-dialog title="新增宿舍" :visible.sync="addOneRoomVisible" width="30%">
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
    </el-dialog>-->
  </el-main>
</template>

<script>
  export default {
    name: "Checkout",
    created(){
      this.getTableData(1);
    },
    methods: {
      checkoutStatusF(row){
        console.log(JSON.stringify(row));return;
        /*switch(row.customer.checkoutStatus){
          case 0: return "未退住";
          case 1: return "退住中";
          case 2: return "已退住";
        }*/
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
      return{
        CheckoutForm: {
          cusName: ""
        },
        tableChecked: [], //选中显示的值
        checkoutData: [],
        page: {
          currentPage: 1, // 当前页
          pageSize: 10, // 每页显示条目个数
          totalCount: 0 // 总条目数
        },
        // 表格高度
        tableHeight: window.innerHeight - 280,
      };
    }
  }
</script>

<style scoped>

</style>