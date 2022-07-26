import React, {ChangeEventHandler, useEffect, useState} from 'react';
import { Layout, Menu, Modal, Avatar, Input, Button, Tooltip,Pagination} from 'antd';
import 'antd/dist/antd.css';

import style from './index.module.scss';
import {
    FilterOutlined,
    AntDesignOutlined,
    PlusSquareOutlined,
    FundViewOutlined,
} from '@ant-design/icons';
import logo from '../../assets/logo.png';
import ViewContact from "../contacts/View";
import AddContact from "../contacts/Add";
const { Header, Content, Footer, Sider } = Layout;
const { Search } = Input;

const Home = ()=>{

    const [collapsed, setCollapsed] = useState(false);
    const [currentPage,setCurrentPage] = useState<string>("view-contacts-page");
    const [isModalVisible, setIsModalVisible] = useState<boolean>(false);

    const onRouteChange = () => {
        showModal();
    };
    const onSearch = (value:string) => {

    };

    const onSelectMenuItem = (key:string) => {
        if(key.match("filter")){
            setCurrentPage("filter-contacts-page");
        }else if(key.match("view")){
            setCurrentPage("view-contacts-page");
        }
    }



    const getItem = (label:string, key : string, icon ?: any, children ?: any) => {
        return {
            key,
            icon,
            children,
            label,
        };
    }

    const onChangeText: ChangeEventHandler<HTMLInputElement> = (e) => {
        e.preventDefault();

    };

    const showModal = () => {
        setIsModalVisible(true);
    };


    const items = [
        getItem('Filter Contacts', 'filter', <FilterOutlined />),
        getItem('View Contacts', 'view', <FundViewOutlined />),
    ];

    return (
        <Layout
            style={{
                minHeight: '100vh',
            }}
        >
            <Sider collapsible collapsed={collapsed} onCollapse={(value) => setCollapsed(value)}>
                <Avatar
                    size={{
                        xs: 24,
                        sm: 32,
                        md: 40,
                        lg: 64,
                        xl: 80,
                        xxl: 100,
                    }}
                    style={{padding:10}}
                    src={<img src={logo} alt="Phone Book"/>}
                    icon={<AntDesignOutlined />}
                />
                <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline" items={items} onSelect={({ item, key, keyPath, domEvent })=>{
                    onSelectMenuItem(key);
                }} />
            </Sider>
            <Layout className={style.siteLayout}>
                <Header style={{padding:0,minHeight: '10vh'}}>
                    <div style={{padding:10,display:'flex',flexDirection:'row',height:'100%', justifyContent:'space-between', alignItems:'center', alignContent:'center', width:'100%'}}>
                        <h1 style={{fontWeight: 'bold',color:'white'}}>Phone Book</h1>
                        <div style={{display:'flex',height:'100%',flexDirection:'row', justifyContent:'center', alignItems:'center', alignContent:'center', width:'30%',padding:0}}>
                                <Search
                                    placeholder="input search text"
                                    onSearch={onSearch}
                                    size="small"
                                    style={{
                                        width: 200,
                                    }}
                                    onChange={onChangeText}
                                />
                            <Tooltip title="Add New Contact">
                                <Button style={{marginLeft:10}} type="primary" shape="circle" icon={<PlusSquareOutlined />} onClick={onRouteChange}/>
                            </Tooltip>
                            <Avatar src="https://joeschmoe.io/api/v1/random" size={40} style={{marginLeft:20}}/>
                        </div>

                    </div>
                </Header>

                <Content style={{display:'flex',height:'100%',flexDirection:'row',alignItems:'center', justifyContent:'center'}}>
                    {
                        currentPage.match("view-contacts-page") ? <ViewContact /> : currentPage.match("filter-contacts-page") ? <>filter Page</> :  null
                    }
                    <Modal title="Add Contact" maskClosable={false} visible={isModalVisible} onCancel={() => setIsModalVisible(false)} footer={null}>
                        <AddContact setModelVisibleOrInvisible={setIsModalVisible} title="Add Contact" />{/*contacts={contacts} addContact={setContacts} displayBtn={false} currentContact={currentContact} closeModel={setIsModalVisible}*/}
                    </Modal>
                </Content>
                <Footer
                    style={{
                        flexDirection:'column',
                        display:'flex',
                        justifyContent:'center',
                        textAlign: 'center',
                        height:'5%'
                    }}
                >
                    eSolutions ©2022 Created by Muhammad Faisal
                </Footer>
            </Layout>
        </Layout>
    );
}

export default Home;