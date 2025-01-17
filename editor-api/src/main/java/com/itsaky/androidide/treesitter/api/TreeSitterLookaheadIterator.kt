/*
 *  This file is part of AndroidIDE.
 *
 *  AndroidIDE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidIDE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.itsaky.androidide.treesitter.api

import com.itsaky.androidide.treesitter.TSLanguage
import com.itsaky.androidide.treesitter.TSLookaheadIterator
import com.itsaky.androidide.utils.DefaultRecyclable
import com.itsaky.androidide.utils.RecyclableObjectPool

/**
 * @author Akash Yadav
 */
class TreeSitterLookaheadIterator @JvmOverloads internal constructor(pointer: Long = 0) :
  TSLookaheadIterator(pointer), RecyclableObjectPool.Recyclable by DefaultRecyclable(),
  TSSynchronized by DefaultSynchronized() {

  companion object {

    @JvmStatic
    fun obtain(pointer: Long): TreeSitterLookaheadIterator {
      return obtainFromPool<TreeSitterLookaheadIterator>().apply {
        nativeObject = pointer
      }
    }
  }

  override fun next(): Boolean {
    return withLock {
      super.next()
    }
  }

  override fun getCurrentSymbol(): Short {
    return withLock {
      super.getCurrentSymbol()
    }
  }

  override fun getCurrentSymbolName(): String {
    return withLock {
      super.getCurrentSymbolName()
    }
  }

  override fun resetState(stateId: Short): Boolean {
    return withLock {
      super.resetState(stateId)
    }
  }

  override fun reset(language: TSLanguage?, stateId: Short): Boolean {
    return withLock {
      super.reset(language, stateId)
    }
  }

  override fun getLanguage(): TSLanguage {
    return withLock {
      super.getLanguage()
    }
  }

  override fun close() {
    withLock {
      super.close()
      recycle()
    }
  }

  override fun recycle() {
    this.nativeObject = 0
    this.langName = null
    returnToPool()
  }
}