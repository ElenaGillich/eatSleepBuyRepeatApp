import './App.css'
import {Route, Routes} from "react-router-dom";
import AllProducts from "./AllProducts.tsx";
import Navbar from "./Navbar.tsx";

function App() {


    return (
        <>
            <Navbar/>
            <Routes>
                <Route path={"/allProducts"} element={<AllProducts/>}/>
            </Routes>

        </>
    )
}

export default App
