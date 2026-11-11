import { styled } from "styled-components";

type KpiCardProps = {
    label: string;
    count: number;
    highlighted?: boolean;
}
const Label = styled.p`
    margin: 0;
    font-size: 24px;
`;

const Value = styled.p`
    margin: 8px 0 0;
    font-size: 22px;
`;

const Card = styled.div.attrs({ className: "box"})<{ $highlighted?: boolean }>`
&& {    
padding: 24px 16px;
}

    ${({ $highlighted}) =>
        $highlighted &&
        `
            background: repeating-linear-gradient(-45deg, var(--background-color) 0px, var(--background-color) 8px, #ddd 8px, #ddd 9px);
        `}
`;

export default function KpiCard({ label, count, highlighted }: KpiCardProps){
    return (
        <Card $highlighted={highlighted}>
            <Label>{label}</Label>
            <Value>{count}</Value>
        </Card>
    );
}