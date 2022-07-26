import React, {FC, useEffect, useState} from "react";
import 'antd/dist/antd.css';
import {
    Form,
    Input,
    Select,
    Button,
} from 'antd';
import axios from "axios";
import {addContact, updateContact} from "../../../config";
import {Contact} from "../../../data_types";
const { Option } = Select;

interface ModelMethods{
    setModelVisibleOrInvisible ?: (visible:boolean)=> void,
    contact ?: Contact,
    title : string,
    contactList ?:Contact[],
    updateContactList ?: (contact : Contact[]) => void
}

const AddContact : FC<ModelMethods> = ({setModelVisibleOrInvisible,contact,title,contactList,updateContactList}) : JSX.Element => {
    console.log("Received Contact is : ", contact)
    const [form] = Form.useForm();
    const mWidth = '100%';

    const options = [
        {
            value: 'Developer',
            label:'Developer'
        },
        {
            value: 'Project Manager',
            label:'Project Manager'
        },
        {
            value: 'HR Manager',
            label: 'HR Manager'
        },
        {
            value: 'Sales Department',
            label:'Sales Department',
        },
        {
            value: 'DevOps Team',
            label:'DevOps Team',
        },
        {
            value: 'Architect',
            label:'Architect',
        },
    ];

    const formItemLayout = {
        labelCol: {
            span: 8,
        },
        wrapperCol: {
            span: 16,
        },
    };

    const tailFormItemLayout = {
        wrapperCol: {
            xs: {
                span: 24,
                offset: 0,
            },
            sm: {
                span: 16,
                offset: 8,
            },
        },
    };

    const prefixSelector = (
        <Form.Item name="prefix" noStyle>
            <Select
                style={{
                    width: 70,
                }}
            >
                <Option value="40">+40</Option>
                <Option value="44">+44</Option>
            </Select>
        </Form.Item>
    );

    useEffect(() => form.resetFields(), [contact]);

    function onFinish(values: any) {
        console.log("After Submitting Form Values are : ",values.name," - ",values.email," - ",values.prefix,"-",values.phone," - ",values.role);

        if(title.match("Update Contact")){
            axios.put(updateContact+`/${contact?.id}`,{
                "name": values.name,
                "email":values.email,
                "phoneNumber":(values.prefix+values.phone),
                "role":values.role
            }).then(r=>{
                console.log("Data is updated : ",r.data);
                if(contact && contactList && updateContactList){
                    const itemIndex = contactList.findIndex(x => x.id === contact.id )

                    const item = contactList[itemIndex]

                    item.name = values.name;

                    item.email = values.email;

                    item.phoneNumber = (values.prefix + values.phone)

                    item.role = values.role

                    contactList[itemIndex] = item

                    if (setModelVisibleOrInvisible) {
                        setModelVisibleOrInvisible(false)
                    }

                    updateContactList(contactList);
                }
            })
        }else{
            axios.post(addContact, {
                "name": values.name,
                "email":values.email,
                "phoneNumber":values.prefix+values.phone,
                "role":values.role
            }).then((r)=>{
                console.log(r.data);
                form.resetFields();
                if (setModelVisibleOrInvisible) {
                    setModelVisibleOrInvisible(false)
                }
            }).catch(e=>{
                console.log("Error while deleting contact : ", e)
            })
        }
    }

    return (
        <div style={{display:'flex',flexDirection:'column',alignItems:'center',justifyContent:'center'}}>
            <h1 style={{fontWeight:"bold",fontSize:40}}>{title}</h1>

            <Form
                {...formItemLayout}
                form={form}
                name={title}
                onFinish={onFinish}
                initialValues={{
                    prefix:contact ? contact.phoneNumber.slice(0,2) : '40',
                    name:contact ? contact.name : null ,
                    email:contact ? contact.email : null,
                    phone:contact ? contact.phoneNumber.slice(2,contact.phoneNumber.length) : null,
                    role:contact?.role,
                }}
                scrollToFirstError
                style={{maxWidth: mWidth , padding:"15px"}}>

                <Form.Item
                    name="email"
                    label="E-mail"
                    rules={[
                        {
                            type: 'email',
                            message: 'The input is not valid E-mail!',
                        },
                        {
                            required: true,
                            message: 'Please input your E-mail!',
                        },
                    ]}>
                    <Input />
                </Form.Item>


                <Form.Item
                    name="name"
                    label="Full Name"
                    tooltip="What do you want others to call you?"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your nickname!',
                            whitespace: true,
                        },
                    ]}
                >
                    <Input />
                </Form.Item>


                <Form.Item
                    name="phone"
                    label="Phone Number"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your phone number!',
                        },
                    ]}
                >
                    <Input
                        addonBefore={prefixSelector}
                        style={{
                            width: '100%',
                        }}
                    />
                </Form.Item>

                <Form.Item
                    name="role"
                    label="Role"
                    rules={[
                        {
                            required: true,
                            message: 'Please select role for this contact',
                        },
                    ]}
                >
                    <Select
                        showArrow
                        style={{
                            width: '100%',
                        }}
                        allowClear={true}
                        bordered={true}
                        options={options}
                    />
                </Form.Item>

                <Form.Item {...tailFormItemLayout}>
                    <Button block type="primary" htmlType="submit">
                        {title}
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
}

export default AddContact;