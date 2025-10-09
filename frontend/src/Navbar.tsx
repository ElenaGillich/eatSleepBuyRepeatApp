import {useNavigate} from "react-router-dom";

export default function Navbar() {
    const nav = useNavigate();

    return (

        <header className="header">
            <div className="logo-container">
                <img src="/logo.png" alt="Logo" className="logo"/>
            </div>
            <nav className={"navbar"}>
                <button className={"nav-button"} onClick={() => nav("/")}>Home</button>
                <button className={"nav-button"} onClick={() => nav("/allProducts")}>All Products</button>
                <button className={"nav-button"} onClick={() => nav("/allGroceryLists")}>All Grocery Lists</button>
            </nav>
        </header>
    )
}