package com.home.live.bet.new

/**
 *
 * @ Author  QinTian
 * @ Date  2020/11/13
 * @ Describe
 *
 */
object CalculationGame {

    /**
     * 组合选择（从列表中选择n个组合）
     * @param dataList 待选列表
     * @param n 选择个数
     */
    fun combination(dataList: MutableList<String>, n: Int): MutableList<MutableList<String?>> {
        val result = mutableListOf<MutableList<String?>>()
        combinationSelect(dataList, 0, arrayOfNulls(n), 0, result)
        return result
    }


    /**
     * 组合选择
     * @param dataList 待选列表
     * @param dataIndex 待选开始索引
     * @param resultList 前面（resultIndex-1）个的组合结果
     * @param resultIndex 选择索引，从0开始
     */
    private fun combinationSelect(
        dataList: MutableList<String>,
        dataIndex: Int,
        resultList: Array<String?>,
        resultIndex: Int,
        res: MutableList<MutableList<String?>>
    ) {
        val resultLen = resultList.size
        val resultCount = resultIndex + 1
        if (resultCount > resultLen) { // 全部选择完时，输出组合结果
            res.add(resultList.toMutableList())
            return
        }
        for (i in dataIndex until dataList.size + resultCount - resultLen) {
            resultList[resultIndex] = dataList[i]
            combinationSelect(dataList, i + 1, resultList, resultIndex + 1, res)
        }
    }

    /**
     * 计算有多少种组合
     */
    fun combinationNum(total: Int, select: Int): Int {
        var a = total
        var b = select
        var da = 1
        var xian = 1
        //5！/(5-3)!
        for (i in 0 until b) {
            da *= a
            a--
        }
        //3！
        while (b > 0) {
            xian *= b
            b--
        }
        return da / xian
    }


    /**
     * 数组转字符串以逗号分隔
     */
    fun listToString(list: List<String>?): String? {
        val sb = StringBuilder()
        if (list != null && list.isNotEmpty()) {
            for (i in list.indices) {
                if (i < list.size - 1) {
                    sb.append(list[i] + ",")
                } else {
                    sb.append(list[i])
                }
            }
        }
        return sb.toString()
    }


    /**
     * 多个数组排列组合
     * @param list 原始list
     * @param <T> 数据类型
     * @return
    </T> */
    private fun <T> getDescartes(list: List<List<T>>): List<List<T>> {
        val returnList: MutableList<List<T>> = ArrayList()
        descartesRecursive(list, 0, returnList, ArrayList())
        return returnList
    }

    /**
     * 递归实现
     * 原理：从原始list的0开始依次遍历到最后
     *
     * @param originalList 原始list
     * @param position     当前递归在原始list的position
     * @param returnList   返回结果
     * @param cacheList    临时保存的list
     */
    private fun <T> descartesRecursive(
        originalList: List<List<T>>,
        position: Int,
        returnList: MutableList<List<T>>,
        cacheList: MutableList<T>
    ) {
        val originalItemList = originalList[position]
        for (i in originalItemList.indices) {
            //最后一个复用cacheList，节省内存
            val childCacheList =
                if (i == originalItemList.size - 1) cacheList else ArrayList(cacheList)
            childCacheList.add(originalItemList[i])
            if (position == originalList.size - 1) { //遍历到最后退出递归
                returnList.add(childCacheList)
                continue
            }
            descartesRecursive(originalList, position + 1, returnList, childCacheList)
        }
    }

    /**
     * 多个数组排列组合
     * @param list 原始list
     * @return
     */
    fun getPermutations(list: List<List<String>>): List<String>? {
        val resultList: MutableList<String> = ArrayList()
        val list1 = getDescartes(list)
        if (list1.isNotEmpty()) {
            list1.forEach { temp ->
                val str = listToStr(temp)
                if (str.isNotEmpty()) {
                    resultList.add(str)
                }
            }
        }
        return resultList
    }

    private fun listToStr(list: List<String>): String {
        val str = StringBuffer()
        for ((index,item) in list.withIndex()){
            if (index == 0) str.append(item) else str.append(",$item")
        }
        return str.toString()
    }

}