package rv.daimhim.rvdecoration.decoration.core

import rv.daimhim.rvdecoration.DecorationBuilder
import rv.daimhim.rvdecoration.RecycleDecoration

interface CreateDecoration {
    fun createDecoration(pLRecycleDecoration: RecycleDecoration, p: DecorationBuilder.DecorationParams)
}