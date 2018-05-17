/*
 * Copyright (c) www.bugull.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hoolai.bi.mongo.encoder;

import com.hoolai.bi.mongo.annotations.Default;
import com.hoolai.bi.mongo.annotations.Embed;
import com.hoolai.bi.mongo.utils.MapperUtil;
import java.lang.reflect.Field;

/**
 *
 * @author Frank Wen(xbwen@hotmail.com)
 */
public class EmbedEncoder extends AbstractEncoder{
    
    public EmbedEncoder(Object obj, Field field){
        super(obj, field);
    }
    
    @Override
    public String getFieldName(){
        String fieldName = field.getName();
        Embed embed = field.getAnnotation(Embed.class);
        String name = embed.name();
        if(!name.equals(Default.NAME)){
            fieldName = name;
        }
        return fieldName;
    }
    
    @Override
    public Object encode(){
        Class<?> type = field.getType();
        if(type.isEnum()){
            return value.toString();
        }
        return MapperUtil.toDBObject(value);
    }
    
}
