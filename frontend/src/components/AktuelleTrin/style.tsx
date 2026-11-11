import { styled } from "styled-components";
import type { Step } from "./index";
export const Card = styled.div.attrs({ className: "box"})`
&& {
    padding: 0px;
}
`;

export const StepHeader = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px 20px;
    border-bottom: var(--border-sm);
`;

export const HeaderLabel = styled.p`
    margin: 0px;
    font-size: 11px;
    color: #999;
`;

export const HeaderTitle = styled.p`
    margin: 4px 0 0;
    font-weight: bold;
`;

export const StepRow = styled.div`
    display: flex;
    gap: 16px;
    padding: 12px 20px;
`;

export const StepCircle = styled.span<{ $status: Step["status"] }>`
    display: flex;
    align-items: center;
    justify-content: center;
    width: 28px;
    height: 28px;
    border-radius: 50%;
    border: var(--border-sm);
    font-size: 12px;
    flex-shrink: 0;

    ${({ $status}) =>
        $status === "current" && 
    `
    background: var(--primary-color); 
    color: white;
    `}

    ${({ $status}) =>
        $status === "upcoming" && 
    `
    border-color: #ccc; 
    color: #999;
    `}
`;

export const StepText = styled.div`
    flex: 1;
`;

export const StepTitle = styled.p<{ $status: Step["status"] }>`
    margin: 0;
    font-size: 14px;

    ${({ $status }) =>
        $status === "upcoming" &&
        `
            color: #999 
        `}
`;

export const StepDescription = styled.p`
    margin: 4px 0 0;
    font-size: 11px;
    color: #999;
`;

export const StepDate = styled.span`
    font-size: 12px;
`;