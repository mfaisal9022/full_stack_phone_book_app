import React, {FC, useState} from "react"
import 'antd/dist/antd.css'
import 'antd/dist/antd.css'
import {DeleteTwoTone, EditTwoTone} from '@ant-design/icons'
import {Avatar, Card} from 'antd'
import CardDetails from "./details"
import {Contact} from "../../data_types"
const {Meta} = Card

type ContactProps = {
    newContact: Contact;
    handleDelete: (id:number|undefined) => void,
    handleEdit : (contact: Contact)=> void
}

const ContactCard : FC<ContactProps> = (currentContact) => {

    return (
        <Card
            style={{
                width: '30%',
                margin: 10,
            }}
            actions={[
                <DeleteTwoTone key="delete" onClick={()=> currentContact.handleDelete(currentContact.newContact.id)}/>,
                <EditTwoTone key="edit" onClick={()=> currentContact.handleEdit(currentContact.newContact)}/>,
            ]}>

            <Meta
                avatar={<Avatar src="https://joeschmoe.io/api/v1/random" size={60}/>}
                title={currentContact.newContact.role}
                description={<CardDetails name={currentContact.newContact.name} email={currentContact.newContact.email} phoneNumber={currentContact.newContact.phoneNumber} role={currentContact.newContact.role} />}
            />
        </Card>
    )
}

export default ContactCard;