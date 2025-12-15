<template>
  <div>
    <div class="reply" v-if="landlord === null">
      <div class="title">
        好房不愁租，安心当房东
      </div>

      <div class="content">
        做您的专业租赁管家，让好房轻松遇上好租客。
        <span @click="reply" style="text-decoration: underline;cursor: pointer;"
          >申请成为房东</span
        >
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      landlord: {}
    };
  },
  created() {
    this.fetchLandlordData();
  },
  methods: {
    reply() {
      window.open("/reply-landlord", "_blank");
    },
    async fetchLandlordData() {
      try {
        const { data } = await this.$axios.post("/landLord/listUser", {});
        this.landlord = data;
        console.log(this.landlord);
      } catch (error) {
        console.log("查询房东申请信息异常：", error);
      }
    }
  }
};
</script>

<style scoped lang="scss">
.reply {
  background-color: rgb(250, 250, 250);
  padding: 10px 20px;
  box-sizing: border-box;

  .title {
    font-size: 24px;
    font-weight: 800;
    margin-bottom: 10px;
    color: rgb(90, 89, 89);
  }

  .content {
    font-size: 14px;
  }
}
</style>
