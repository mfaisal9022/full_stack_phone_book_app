import React, {useEffect, useState} from 'react'
import {Contact} from "../../../data_types"
import ContactCard from "../../card"
import {Empty, Layout, Modal, Pagination} from "antd"
import Filter from "../Filter"
import {ExclamationCircleOutlined} from "@ant-design/icons"
import axios from "axios"
import { BASE_URL, deleteContact, getAllContacts} from "../../../config"
import AddContact from "../Add";

const {Content } = Layout
const { confirm } = Modal

const ViewContact = () : JSX.Element=>{

    const [contacts ,setContacts]= useState<Contact[]>([])
    const [totalElements,setTotalElements] = useState<number>(0)
    const [currentPageNumber,setCurrentPageNumber] = useState<number>(1)
    const [totalPages,setTotalPages] = useState<number>(0)
    const [filters,setFilter] = useState<string[]>([])
    const [isModalVisible, setIsModalVisible] = useState<boolean>(false);
    const [selectedContact,setSelectedContact] = useState<Contact>();

    const handleEdit : (contact:Contact) => void = (contact:Contact) => {
        console.log("Edit is called "+ contact.id +" - "+ contact.name+ " - "+contact.phoneNumber+" - "+contact.role)
        setSelectedContact(contact)
        showModal();

    }

    const showModal = () => {
        setIsModalVisible(true);
    };

    useEffect(() => {
            if (filters.length > 0) {// fitlers enabled
                callGetContactsApi(BASE_URL + `/filterBy/Roles/${1}/3`);
            } else { //filters not enabled
                callGetContactsApi(getAllContacts + `/${1}/3`)
            }
        },
        [filters]);

    const updateFilter : (updatedFilters : string[]) => void = (updatedFilters : string[]) => {
        setFilter(updatedFilters);
    }

    const handleDelete  = (id : number | undefined)=>{
        confirm({
            title: 'Are you sure delete this Contact?',
            icon: <ExclamationCircleOutlined />,
            content: "This will permanently and you can't restore it !!!",
            okText: 'Yes',
            okType: 'danger',
            cancelText: 'No',

            onOk() {
                console.log('OK'," - ",id);
                axios.delete(deleteContact+`/${id}`)
                    .then(r=>{
                        setContacts(contacts.filter((item:Contact) => item.id !== id));
                        setTotalElements(totalElements-1)
                        if((totalElements-1) % 3 === 0 && (contacts.length-1) === 0){
                            if(currentPageNumber === totalPages){
                                onMoveToNextPage((totalElements-1)/3,3);
                            }else{
                                onMoveToNextPage(currentPageNumber,3);
                            }
                        }
                    })
                    .catch( e => {
                        console.log("error ", e);
                    })
            },

            onCancel() {
                console.log('Cancel');
            },
        });
    }

    const callGetContactsApi = (url:string) =>{
        if(filters.length > 0){
            axios.post(url,filters).then(r=>{
                console.log("Filter Data by role POST API : ", r.data)
                setContacts(r.data.content)
                setTotalElements(r.data.totalElements)
                setCurrentPageNumber(r.data.pageable.pageNumber+1)
                setTotalPages(r.data.totalPages)
            }).catch(e=>{
                console.log("Error : ", e)
            })
        }else{
            axios.get(url).then(r=>{//data: JSON.stringify(data)
                console.log("APi Response : ",r.data)
                setContacts(r.data.content)
                setTotalElements(r.data.totalElements)
                setCurrentPageNumber(r.data.pageable.pageNumber+1)
                setTotalPages(r.data.totalPages)
            }).catch(e =>{
                console.log("Error : ", e)
            })
        }

    }

    const onMoveToNextPage : (page:number, pageSize:number) => void = (page:number, pageSize:number)=>{
        if(filters.length > 0){// fitlers enabled
            callGetContactsApi(BASE_URL+`/filterBy/Roles/${page}/3`);
        }else{ //filters not enabled
            callGetContactsApi(getAllContacts+`/${page}/3`)
        }
    }

    return (
        <Content style={{display:'flex',borderBottomStyle:'solid',height:'100%',flexDirection:'row',alignItems:'flex-start', justifyContent:'flex-start'}}>
            <Filter setFilter={updateFilter}/>

            <Content style={{display:'flex',flexDirection:'column',height:'100%'}}>
                <Content style={{display:'flex',height:'90%',flexDirection:'row',flexWrap: 'wrap',alignItems:'center', justifyContent:'center'}}>
                    {
                        contacts.length > 0 ? contacts.map((item ,index) => {
                            return <ContactCard key={index} newContact={item} handleDelete={handleDelete} handleEdit={handleEdit} />
                        }) : <Empty />
                    }
                </Content>

                <Content style={{display:'flex',alignItems:'center',justifyContent:'center'}}>
                    <Pagination
                        total={totalElements}
                        showTotal={(total, range) => `${range[0]}-${range[1]} of ${total} items`}
                        defaultPageSize={3}
                        defaultCurrent={currentPageNumber}
                        current={currentPageNumber}
                        onChange={onMoveToNextPage}
                    />
                </Content>

                <Modal title="Update Contact" maskClosable={false} visible={isModalVisible} onCancel={() => setIsModalVisible(false)} footer={null}>
                    <AddContact setModelVisibleOrInvisible={setIsModalVisible} contact={selectedContact} title="Update Contact" contactList={contacts} updateContactList={setContacts}/>
                </Modal>
            </Content>
        </Content>
    )
}

export default ViewContact;