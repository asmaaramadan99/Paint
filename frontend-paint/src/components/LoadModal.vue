<template>
  <transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container" style="width: 400px">
          <div class="modal-header"></div>

          <div class="modal-body">
            <input
              type="file"
              id="file"
              ref="file"
              accept=".xml, .json"
              v-on:change="handleFileUpload()"
            />
          </div>
          <div class="modal-footer" style="margin-top: 10%">
            <slot name="footer">
              <v-btn text color="primary" v-on:click="submitFile()">
                Load
              </v-btn>
              <br />
              <v-btn id="close" text color="error" @click="$emit('close')">
                Cancel
              </v-btn>
            </slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>
<script>
import PaintService from "../service/PaintService";
export default {
  data() {
    return {
      file: "",
      extension:""
    };
  },
  methods: {
    submitFile() {
      if (this.$refs.file.files[0] == null) {
        return
      }
      let formData = new FormData();
      formData.append("file", this.file);
      formData.append("ext", this.extension);
      PaintService.load(formData, this.extension)
        .then(Response => {
          PaintService.getName().then( response => {
            this.$root.$emit("nameChanged", response.data);
          });
          this.$root.$emit('loaded',Response.data);
        })
        .catch(function() {
          console.log("FAILURE LOADING FILE");
        });
      document.getElementById("close").click();
    },
    handleFileUpload() {
      if (this.$refs.file.files.length === 0) {
        return
      }
      this.file = this.$refs.file.files[0];
      let fileName = this.file.name;
      let regex = new RegExp('[^.]+$');
      this.extension = fileName.match(regex);
    }
  }
};
</script>
<style>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
  width: 300px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  font-family: Helvetica, Arial, sans-serif;
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}

.modal-default-button {
  float: right;
}

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>
