import Vue from "vue"

//Vue.directive('color', (el, binding) => {
//  el.style.color = binding.value
//})
//
//Vue.directive("backgroundColor", (el, binding) => {
//  el.style.backgroundColor = binding.value
//})

Vue.directive("align-child", (el, binding) => {
  el.style.alignItems = binding.value
})

Vue.directive("justify-child", (el, binding) => {
  el.style.justifyContent = binding.value
})
