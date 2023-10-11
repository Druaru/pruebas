const app = Vue.createApp({
  data() {
    return {
      counter: 11,
      name: '',
      confirmedName: ''
    };
  },
  methods: {
    confirmInput(){
      this.confirmedName = this.name;
    },
    submitForm() {
      alert('submitted');
    },
    setName(event, lastname) {
      this.name = event.target.value + ' ' + lastname;
    },
    add(num) {
      this.counter += num;
    },
    less(num) {
      this.counter -= num;
    }
  }

});

app.mount('#events');
