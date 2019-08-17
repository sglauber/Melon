/*
 * This file is part of the UNES Open Source Project.
 * UNES is licensed under the GNU GPLv3.
 *
 * Copyright (c) 2019.  João Paulo Sena <joaopaulo761@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.forcetower.uefs.core.storage.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.forcetower.uefs.core.model.unes.SagresFlags

@Dao
interface FlagsDao {
    @Insert(onConflict = REPLACE)
    fun insertFlags(flag: SagresFlags)

    @Query("SELECT * FROM SagresFlags LIMIT 1")
    fun getFlags(): LiveData<SagresFlags?>

    @Query("UPDATE SagresFlags SET demand_open = :demandOpen")
    fun updateDemand(demandOpen: Boolean)

    @Query("SELECT * FROM SagresFlags LIMIT 1")
    fun getFlagsDirect(): SagresFlags?
}