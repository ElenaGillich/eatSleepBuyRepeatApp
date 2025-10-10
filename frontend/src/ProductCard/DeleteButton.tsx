import React from 'react';

interface Props {
    productId: string;
    onDelete?: (id: string) => void;
}

const DeleteButton: React.FC<Props> = ({ productId, onDelete }) => {
    const handleDelete = async () => {
        if (!confirm('Delete this product?')) {
            return;
        }

        try {
            const response = await fetch(`/api/products/${productId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Failed to delete item');
            }

            onDelete?.(productId);
        } catch (error) {
            console.error('Error deleting item:', error);
        }
    };

    return (
        <button
            onClick={handleDelete}
            className="delete-button"
        >x</button>
    );
};

export default DeleteButton;