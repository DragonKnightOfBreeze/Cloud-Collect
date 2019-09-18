* el-radio/el-checkbox标签的label属性对应原生标签的value属性，标签的内容对应原生标签的对应label标签的内容，即显示文本。
* el-select标签的label属性对应显示的文本，value属性对应原生标签的value属性。
* 如果属性的值是数字/布尔值，即使不包含变量，仍然需要使用Vue属性绑定语法，如`:min="0"`。
* 属性绑定语法中的方法不需要添加括号。计算属性自然也不需要添加。
* 插槽中的slot-scope属性已废弃，使用v-slot:default或#default替代。
