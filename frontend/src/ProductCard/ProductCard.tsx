import type {Product} from "../model/Product.tsx";
import './ProductCard.css'
import {useState, useEffect} from "react";
import axios from "axios";
import DeleteButton from "./DeleteButton.tsx";

type ProductCardProps = {
    product: Product;
    handleDeleteFromList: (id: string) => void;
}

export default function ProductCard(props: Readonly<ProductCardProps>) {

    const [isEditing, setIsEditing] = useState(false);
    const [name, setName] = useState(props.product.name);

    function updateProduct() {
        axios.put(`/api/products/${props.product.id}`, {name: name})
            .then((response) => setName(response.data.name))
            .catch((e) => console.log(e))
    }

    const [isCopied, setIsCopied] = useState(false);

    useEffect(() => {
        const timeoutId = setTimeout(() => {
            setIsCopied(false);
        }, 2000);
        return () => clearTimeout(timeoutId);
    }, [copyToClipboard]);

    function copyToClipboard() {
        navigator.clipboard.writeText(props.product.id);
        console.log("Copied ID to Clipboard: ", props.product.id)
        setIsCopied(true);
    }

    return (
        <div className={"product"}>
            {!isEditing && <h2>{name}</h2>}
            {isEditing && <input type={"text"} value={name} onChange={(e) => setName(e.target.value)}/>}

            {!isCopied && <button onClick={copyToClipboard}>📎 Copy ID</button>}
            {isCopied && <button className={"button-copied"}>✓ Copied!</button>}

            {!isEditing && <button onClick={() => setIsEditing(!isEditing)}> ✎Edit</button>}
            {isEditing && <button onClick={() => {
                updateProduct();
                setIsEditing(!isEditing)
            }}>Save</button>}
            {!isEditing && <DeleteButton productId={props.product.id} onDelete={props.handleDeleteFromList}/>}
        </div>
    )
}