package rv.daimhim.rvdecoration.decoration.base

import rv.daimhim.rvdecoration.DecorationBuilder
import rv.daimhim.rvdecoration.RecycleDecoration

interface CreateDecoration {
    fun createDecoration(pLRecycleDecoration: RecycleDecoration, p: DecorationBuilder.DecorationParams)
}