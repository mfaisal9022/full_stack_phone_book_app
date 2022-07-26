const port = 8080;// port 8080 for spring boot and port 3000 for node.js

export const BASE_URL = `http://localhost:${port}/api/contacts`;
export const getAllContacts = `${BASE_URL}/all/page`;
export const getContactById = `${BASE_URL}`;
export const addContact = `${BASE_URL}/add`;
export const deleteContact = `${BASE_URL}/delete`;
export const updateContact = `${BASE_URL}/update`;