import {Divider} from "antd";
import React, {FC} from "react";
import {Contact} from "../../../data_types";


const CardDetails : FC<Contact> = (currentContact) : JSX.Element => {
    return <div>
        <Divider plain={true} dashed={true} orientation="left" orientationMargin="0">Name</Divider>
        <p>{currentContact.name}</p>
        <Divider plain={true} dashed={true} orientation="left" orientationMargin="0">Email</Divider>
        <p>{currentContact.email}</p>
        <Divider plain={true} dashed={true} orientation="left" orientationMargin="0">Phone Number</Divider>
        <p>{currentContact.phoneNumber}</p>
    </div>
}

export default CardDetails;