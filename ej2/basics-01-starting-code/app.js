const app = Vue.createApp({
    data() {
        return {
            courseGoal: 'finish the course and learn Vue',
            courseGoalA: 'Master Vue and build amazing apps!!',
            vueLink: 'https://vuejs.org'
        };
    },
    methods: {
        outputGoal: function(){
            const randomNumber = Math.random();
            if(randomNumber < 0.5){
                return this.courseGoal;
            } else {
                return this.courseGoalA;
            }
        }
    }
});
app.mount('#user-goal');