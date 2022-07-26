import React from 'react';
import {Route, BrowserRouter as Router, Routes, Link} from "react-router-dom";
import Home from "./components/home";

function App() {
    return (
        <Router>
            <nav>
                <Link to="/addContact"></Link>
                <Link to="/"></Link>
            </nav>

            <Routes>
                <Route path="/" element={<Home />} />
            </Routes>
        </Router>
    );
}

export default App;
