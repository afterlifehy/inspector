package com.rt.base.adapter

import com.chad.library.adapter.base.BaseNodeAdapter
import com.chad.library.adapter.base.entity.node.BaseNode
import com.rt.base.adapter.BaseItemProvider

abstract class BaseNodeProvider : BaseItemProvider<BaseNode>() {

    override fun getAdapter(): BaseNodeAdapter? {
        return super.getAdapter() as? BaseNodeAdapter
    }

}