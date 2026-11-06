import { styled } from "styled-components";

const Bar = styled.header`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px 50px;
    border-bottom: var(--border-sm);
`;

const HelpButton = styled.button`
    width: 32px;
    height: 32px;
    border-radius: 50%;
    border: var(--border-sm);
    background: transparent;
    cursor: pointer;
`;

export default function Header(){
    return (
        <Bar>
            <strong>Place2Book</strong>
            <HelpButton>?</HelpButton>
        </Bar>
    );
}