package com.example.holybibleapp.core

import junit.framework.Assert.assertTrue
import org.junit.Test
import java.io.IOException
import java.lang.Exception

class AbstractTest {

    @Test
    fun test_success() {
        val dataObject = TestDataObject.Success("a", "b")
        val domainObject = dataObject.map(DataMapper.Base())
        assertTrue(domainObject is DomainObject.Success)
    }
    @Test
    fun test_fail() {
        val dataObject = TestDataObject.Fail(IOException())
        val domainObject = dataObject.map(DataMapper.Base())
        assertTrue(domainObject is DomainObject.Fail)
    }

    sealed class TestDataObject : Abstract.Object<DomainObject, DataMapper>() {
        abstract override fun map(mapper: DataMapper): DomainObject

        class Success(
            private val textOne: String,
            private val textTwo: String
        ) : TestDataObject() {
            override fun map(mapper: DataMapper): DomainObject {
                return mapper.map(textOne, textTwo)
            }
        }

        class Fail(private val exception: Exception) : TestDataObject() {
            override fun map(mapper: DataMapper): DomainObject {
                return mapper.map(exception)
            }
        }
    }

    interface DataMapper : Abstract.Mapper {

        fun map(textOne: String, textTwo: String): DomainObject

        fun map(exception: Exception): DomainObject

        class Base : DataMapper {
            override fun map(textOne: String, textTwo: String): DomainObject {
                return DomainObject.Success("$textOne $textTwo")
            }

            override fun map(exception: Exception): DomainObject {
                return DomainObject.Fail()
            }
        }
    }

    sealed class DomainObject : Abstract.Object<UiObject, DomainToUIMapper>() {

        class Success(private val textCombined:String) : DomainObject() {
            override fun map(mapper: DomainToUIMapper): UiObject {
                TODO("not done yet")
            }
        }

        class Fail: DomainObject() {
            override fun map(mapper: DomainToUIMapper): UiObject {
                TODO("not done yet")
            }
        }
    }
    interface DomainToUIMapper : Abstract.Mapper

    sealed class UiObject : Abstract.Object<Unit, Abstract.Mapper.Empty>() {

    }
}