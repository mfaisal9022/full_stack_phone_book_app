import React, {ChangeEventHandler, FC, useRef} from 'react'

import {Checkbox, Button, Input} from 'antd';
import {CheckboxChangeEvent} from "antd/es/checkbox";

const plainOptions = ['Developer', 'Project Manager', 'HR Manager','Sales Department','DevOps Team','Architect'];
const { Search } = Input;

interface Filter {
    setFilter : (filters:string[]) => void;
}
const Filter : FC<Filter> = ({setFilter}) : JSX.Element =>{

    const masterFilter = useRef<string[]>([])

    const onSelectFilterOption : (e : CheckboxChangeEvent) => void = (e: CheckboxChangeEvent) => {
        if(e.target.checked){
            masterFilter.current.push(e.target.value)
        }else if(!e.target.checked){
            const index : number = masterFilter.current.indexOf(e.target.value)
            if(index > -1){
                masterFilter.current.splice(index,1)
            }
        }
    }

    const onFilterSubmit = (event:any) =>{
        setFilter([...masterFilter.current]);
    }

    const onSearch = (value:string) => {

    };

    const onChangeText: ChangeEventHandler<HTMLInputElement> = (e) => {
        e.preventDefault();
    };

    return (
        <div style={{padding:10, borderRightStyle:'solid',display:'flex',height:'100%',width:'20%', flexDirection:'column',alignItems:'flex-start', justifyContent:'center'}}>
            <h1 style={{width:'100%',textAlign:'center'}}>Choose Department </h1>
            <Search
                placeholder="input search text"
                onSearch={onSearch}
                size="small"
                style={{
                    width: 200,
                }}
                onChange={onChangeText}
            />
            {
                plainOptions.map((item:string,index:number)=>{
                    return <Checkbox style={{marginLeft:5,marginTop:20}} key={item} defaultChecked={false} value={item} onChange={onSelectFilterOption}>{item}</Checkbox>
                })
            }
            <Button style={{marginTop:20,width:'100%'}} type="primary" block onClick={onFilterSubmit}>
                Filter
            </Button>
        </div>

    )
}

export default Filter;